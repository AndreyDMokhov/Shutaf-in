app.service('webSocketService', function ($q, $sessionStorage, sessionService) {

        var vm = this;

        vm.isConnected = false;
        vm.stompClient = null;
        vm.subscription = null;
        vm.connecting = false;


        /**
         * Checks if authenticated or already connected, then call connect()
         * @returns {*}
         */
        function getConnection() {
            if (!sessionService.isAuthenticated() || vm.isConnected || vm.connecting) {
                return;
            }
            /**
             * this trigger helps us to avoid multiple calls of observerCallback and getConnection func
             */
            vm.connecting = true;
            return $q(function (resolve, reject) {
                connect().then(
                    function (success) {
                        resolve(success);
                    });
            });
        }

        /**
         *  Handshake with web socket (send over HTTP(101) change protocol request)
         *  Receives socket session, stomp headers and records them to stompClient
         *  Will reconnect each second, if error occurs
         *
         *  Backend connected classes:
         *  1. SimpleMessageBroker (spring)
         *  2. ChannelInterceptor (authentication)
         *  3. WebSocketEndpointConfigurer (endpoint prefix path = "/api/socket", line 26)
         *
         * @returns {*}
         */
        function connect() {
            if (!sessionService.isAuthenticated() || vm.isConnected) {
                return;
            }
            vm.connecting = true;
            var socket = getSocket();
            vm.stompClient = Stomp.over(socket);
            /**
             * STOMP debug mode setting. If true -> shows all debug messages in console.log
             */
            vm.stompClient.debug = false;
            return $q(function (resolve, reject) {
                vm.stompClient.connect({'session_id': $sessionStorage.sessionId}, function (success) {
                    vm.connecting = false;
                    vm.isConnected = true;
                    resolve();
                }, function (err) {
                    vm.isConnected = false;
                    notifyObservers();
                    setTimeout(function () {
                        connect();
                    }, 1000);
                });
            });
        }

        /***
         * Second and the main function
         * If there is something in stompClient - we can add subscribe client to any destination
         *
         * @param destination - is chat id, that was received from  /get/chats -> List<Chat> getChats -> ChatController
         */
        function subscribe(destination) {

            var deferred = $q.defer();
            if (!vm.isConnected) {
                getConnection();
            } else {
                vm.subscription = vm.stompClient.subscribe(destination, function (message) {
                    deferred.notify(JSON.parse(message.body));
                }, {'session_id': $sessionStorage.sessionId});
            }
            return deferred.promise;
        }

        /**
         *  vm.subscription.unsubscribe(); unSubscribe all subscriptions via Stomp
         */
        function unSubscribe() {
            if (vm.subscription !== null) {
                vm.subscription.unsubscribe();
                vm.subscription = null;
            }
        }

        /**
         *  Hard coded endpoint and supported protocols
         *  server: 'shutaf-in' -> influences on nothing
         *  This method is being called from connect()
         * @returns {*}
         */
        function getSocket() {
            var protocols = ['xhr-polling', 'xdr-polling', 'xdr-streaming', 'xhr-streaming'];
            var url = '/api/socket';
            return new SockJS(url, null, {transports: protocols, server: 'shutaf-in'});
        }

        /**
         * Stomp client has method send, that receives an address (channel-chat id, sessionId and the message itself)
         * Channel means chat id in @MessageMapping
         * @param message: message itself and its native headers, containing SI sessionId
         * @param address: channel (@MessageMapping info)
         */
        function sendMessage(message, address) {
            vm.stompClient.send(address, {'session_id': $sessionStorage.sessionId}, JSON.stringify(message));
        }

        /**
         *  Cleans out stompClient and sends a notification to the server for disconnection.
         *  The server will receive NO message from (this) the stomp client with such internal stompClient id after disconnect
         * @returns {*}
         */
        function disconnect() {
            return $q(function (resolve, reject) {
                if (vm.isConnected) {
                    vm.stompClient.disconnect().then(function () {
                        vm.isConnected = false;
                        resolve();
                    });
                }
            });
        }

        /**
         *  Whether the service is connected.
         *  Reconnect occurs
         * @returns {boolean}
         */
        function isConnectionReady() {
            return vm.isConnected;
        }

        var observerCallbacks = [];

        /**
         *  register an observer callback. will be called if connection is lost
         */
        function registerObserverCallback(callback) {
            if (!observerCallbacks.includes(callback)) {
                observerCallbacks.push(callback);
            }
        }

        /**
         *  call this when connection is lost. it fires all callbacks, that are register above
         */
        var notifyObservers = function () {
            if (!vm.connecting) {
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            }
        };


        vm.getConnection = getConnection;
        vm.isConnectionReady = isConnectionReady;
        vm.disconnect = disconnect;
        vm.connect = connect;
        vm.subscribe = subscribe;
        vm.unSubscribe = unSubscribe;
        vm.sendMessage = sendMessage;
        vm.registerObserverCallback = registerObserverCallback;
    }
);