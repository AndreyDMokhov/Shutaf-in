app.service('webSocketService', function ($q, $sessionStorage, sessionService) {

        var vm = this;

        vm.isConnected = false;
        vm.stompClient = null;

        function getConnection() {
            var socket = getSocket();
            vm.stompClient = Stomp.over(socket);
            vm.stompClient.debug = false;

            return $q(function (resolve, reject) {
                vm.stompClient.connect({'session_id': $sessionStorage.sessionId}, function (frame) {
                    setConnected(true);
                    resolve();
                }, function (err) {
                    if(!sessionService.isAuthenticated()){
                        return;
                    }

                    setConnected(false);
                    setTimeout(function () {
                        getConnection().then(
                            function (success) {
                                setConnected(true);
                                resolve();
                            });
                    }, 1000);
                });
            });
        }

        function getSocket() {
            var protocols = ['xhr-polling', 'xdr-polling', 'xdr-streaming', 'xhr-streaming'];
            var url = '/api/socket';
            var socket = new SockJS(url, null, {transports: protocols, server: 'shutaf-in'});
            return socket;
        }

        function getClient() {
            return $q(function (resolve, reject) {
                if (vm.stompClient === null) {
                    getConnection().then(
                        function (success) {
                            resolve(vm.stompClient);
                        });
                }
                else {
                    resolve(vm.stompClient);
                }
            });
        }

        function disconnect() {
            return $q(function (resolve, reject) {
                if (vm.isConnected) {
                    vm.stompClient.disconnect().then(function () {
                        vm.isConnected = false;
                        resolve();
                    })
                } else {
                    reject();
                }
            });
        }

        function setConnected(boolean) {
            isConnected = boolean;
        }

        function isConnected() {
            return isConnected;
        }

        vm.getConnection = getConnection;
        vm.isConnected = isConnected;
        vm.getClient = getClient;
        vm.disconnect = disconnect;
    }
);