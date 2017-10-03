app.service('webSocketService', function ($q, $sessionStorage, sessionService) {

        var vm = this;

        vm.isConnected = false;
        vm.stompClient = null;
        vm.subscription = null;


        function connect() {
            var socket = getSocket();
            vm.stompClient = Stomp.over(socket);
            // if you comment next line, you will see all messages from stomp in console
            vm.stompClient.debug = false;

            return $q(function (resolve, reject) {
                vm.stompClient.connect({'session_id': $sessionStorage.sessionId}, function (frame) {
                    vm.isConnected = true;
                    resolve();
                }, function (err) {
                    vm.isConnected = false;
                    reject();
                });
            });
        }

        function getConnection() {
            if (!sessionService.isAuthenticated()) {
                return;
            }
            return $q(function (resolve, reject) {
                connect().then(
                    function (succes) {
                        resolve(succes);
                    }
                    , function (error) {
                        setTimeout(function () {
                            getConnection().then(function (succes) {
                                resolve(succes);
                            });
                        }, 1000);
                    });
            });
        }

        function subscribe(destination) {

            var deferred = $q.defer();
            if (vm.stompClient === null) {
                getConnection();
            } else {
                unSubscribe();
                vm.subscription = vm.stompClient.subscribe(destination, function (message) {
                    deferred.notify(JSON.parse(message.body));
                }, {'session_id': $sessionStorage.sessionId});
            }
            return deferred.promise;
        }

        function unSubscribe() {
            if (vm.subscription !== null) {
                vm.subscription.unsubscribe();
                vm.subscription = null;
            }
        }

        function getSocket() {
            var protocols = ['xhr-polling', 'xdr-polling', 'xdr-streaming', 'xhr-streaming'];
            var url = '/api/socket';
            var socket = new SockJS(url, null, {transports: protocols, server: 'shutaf-in'});
            return socket;
        }

        function sendMessage(message, address) {
            vm.stompClient.send(address, {'session_id': $sessionStorage.sessionId}, JSON.stringify(message));
        }

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

        function isConnectionReady() {
            return vm.isConnected;
        }

        vm.getConnection = getConnection;
        vm.isConnectionReady = isConnectionReady;
        vm.disconnect = disconnect;
        vm.connect = connect;
        vm.subscribe = subscribe;
        vm.sendMessage = sendMessage;
    }
);