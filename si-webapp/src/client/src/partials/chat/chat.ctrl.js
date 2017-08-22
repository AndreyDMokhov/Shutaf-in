app.controller('chatController', function (chatModel, $sessionStorage, $stomp, $scope, $q) {
        var vm = this;

        vm.users = {};
        vm.dataLoading = true;
        vm.chatName = null;
        vm.currentChat = {};
        vm.listOfChats = {};
        vm.outMessage = {};
        vm.messages = {};
        vm.usersInChat = {};
        vm.isConnected = false;
        vm.subscription = null;
        function activate() {
            getUserData();
            getChats();
            connect();
        }

        function getUserData() {

            chatModel.getUsers().then(
                function (success) {
                    vm.users = success.data;
                }, function (error) {
                    console.log(error);
                });
        }


        function connect() {

            return $q(function (resolve, reject) {

                $stomp.setDebug(function (args) {
                    console.log(args);
                });
                $stomp
                    .connect('/api/chatsocket', {'session_id': $sessionStorage.sessionId})
                    .then(function (frame) {
                        vm.isConnected = true;
                        vm.dataLoading = false;
                        resolve(console.log('Connected: ' + frame));
                    }, function (err) {
                        reject(console.log(err));
                        $stomp.disconnect();
                        vm.dataLoading = false;
                    });
            });
        }

        function subscribe() {
            vm.dataLoading = true;
            if (!vm.isConnected) {
                connect().then(
                    function (resolve) {
                        _doSubscribe();
                    },
                    function (reject) {
                    });
            }
            else {
                vm.dataLoading = false;
                _doSubscribe();
            }
        }

        function _doSubscribe() {
            vm.subscription = $stomp.subscribe('/subscribe/chat/' + vm.currentChat.id,
                function (payload) {
                    $scope.$apply(function () {
                        vm.messages.push(payload)
                    });
                }, {'session_id': $sessionStorage.sessionId});
        }

        function addChat() {
            vm.dataLoading = true;
            vm.currentChat.id = 0;
            chatModel.addChat(vm.chatName).then(
                function (success) {
                    joinChat({"id": success.headers('chat_id'), "chatTitle": vm.chatName});
                    getChats();
                    vm.chatName = "";
                }, function (error) {
                    vm.dataLoading = false;
                    console.log(error);
                })
        }

        function getChats() {
            chatModel.getChats().then(
                function (success) {
                    vm.listOfChats = success.data;
                }, function (error) {
                    console.log(error);
                })
        }

        function disconnect() {

            return $q(function (resolve, reject) {
                if (vm.isConnected) {
                    unSubscribe();
                    $stomp.disconnect().then(function () {
                        vm.isConnected = false;
                        resolve(console.log('disconnected'));
                    })
                } else {
                    reject(console.log('not connected'));
                }
            });
        }

        function unSubscribe() {
            if (vm.subscription !== null) {
                vm.subscription.unsubscribe();
                vm.subscription = null;
                console.log('unsubscribed');
            }
            else {
                console.log('no subscription');
            }
        }

        function joinChat(chat) {
            if(vm.currentChat.id===chat.id){
                return;
            }
            vm.currentChat = chat;
            unSubscribe();
            subscribe();
            getAllMessages();
            getActiveUsersInChat();
        }

        function getAllMessages() {
            chatModel.getAllMessages(vm.currentChat.id).then(
                function (success) {
                    vm.messages = success.data;
                }, function (error) {
                    console.log(error);
                });
        }

        function sendMessage() {
            if (!vm.outMessage.message || vm.outMessage.message === '') {
                return;
            }
            vm.outMessage.userId = $sessionStorage.userProfile.id;
            vm.outMessage.messageType = 1;
            vm.adress = '/chat/' + vm.currentChat.id + '/message';

            $stomp.send(vm.adress, vm.outMessage, {'session_id': $sessionStorage.sessionId});
            vm.outMessage.message = "";
        }

        function removeUserFromChat(userId) {
            chatModel.removeUserFromChat(vm.currentChat.id, userId).then(
                function (success) {
                    vm.getActiveUsersInChat();
                }, function (error) {
                    console.log(error);
                });
        }

        function getActiveUsersInChat() {
            chatModel.getActiveUsersInChat(vm.currentChat.id).then(
                function (success) {
                    vm.usersInChat = success.data;
                }, function (error) {
                    console.log(error);
                });
        }

        function deleteChat(chatId) {
            chatModel.removeUserFromChat(chatId, $sessionStorage.userProfile.id).then(
                function (success) {
                    //noinspection EqualityComparisonWithCoercionJS
                    if (vm.currentChat.id == chatId) {
                        unSubscribe();
                        vm.messages = {};
                        vm.usersInChat = null;
                        vm.currentChat = {};
                        getChats();
                    }
                    else {
                        getChats();
                    }
                }, function (error) {
                    console.log(error);
                });
        }

        function addUserToChat(userId) {
            chatModel.addUserToChat(vm.currentChat.id, userId).then(
                function (success) {
                    vm.getActiveUsersInChat();
                }, function (error) {
                    console.log(error);
                });
        }

        function exitChat() {
            unSubscribe();
            vm.messages = {};
            vm.usersInChat = null;
            vm.currentChat = {};
        }

        activate();

        vm.getUserData = getUserData;
        vm.addChat = addChat;
        vm.getChats = getChats;
        vm.joinChat = joinChat;
        vm.sendMessage = sendMessage;
        vm.removeUserFromChat = removeUserFromChat;
        vm.getActiveUsersInChat = getActiveUsersInChat;
        vm.deleteChat = deleteChat;
        vm.addUserToChat = addUserToChat;
        vm.exitChat = exitChat;
    }
);