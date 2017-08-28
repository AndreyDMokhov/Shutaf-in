app.controller('chatController', function (chatModel, $sessionStorage, $scope, $q, webSocketService) {
        var vm = this;

        vm.users = {};
        vm.dataLoading = false;
        vm.chatName = null;
        vm.currentChat = {};
        vm.listOfChats = {};
        vm.outMessage = {};
        vm.messages = {};
        vm.usersInChat = {};
        vm.isConnected = webSocketService.isConnected();
        vm.subscription = null;
        vm.stompClient = null;

        function activate() {
            getUserData();
            getChats();
        }

        function getUserData() {
            chatModel.getUsers().then(
                function (success) {
                    vm.users = success.data;
                });
        }

        function subscribe() {
            vm.dataLoading = true;
            if (!webSocketService.isConnected()) {
                setTimeout(function () {
                    subscribe();
                }, 1000);
                return;
            }
            if (vm.stompClient === null) {
                webSocketService.getClient().then(
                    function (resolve) {
                        vm.stompClient = resolve;
                        vm.dataLoading = false;
                        _doSubscribe();
                    }
                )
            }
            else {
                vm.dataLoading = false;
                _doSubscribe();
            }
        }

        function _doSubscribe() {
            vm.subscription = vm.stompClient.subscribe('/subscribe/chat/' + vm.currentChat.id,
                function (payload) {
                    showChatMessage(JSON.parse(payload.body));
                }, {'session_id': $sessionStorage.sessionId});
        }

        function unSubscribe() {
            if (vm.subscription !== null) {
                vm.subscription.unsubscribe();
                vm.subscription = null;
            }

        }

        function showChatMessage(message) {
            $scope.$apply(function () {
                vm.messages.push(message)
            });
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
                })
        }

        function getChats() {
            chatModel.getChats().then(
                function (success) {
                    vm.listOfChats = success.data;
                })
        }

        function joinChat(chat) {
            if (vm.currentChat.id === chat.id) {
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
                });
        }

        function sendMessage() {
            if (!vm.outMessage.message || vm.outMessage.message === '') {
                return;
            }
            vm.outMessage.messageType = 1;
            vm.adress = '/chat/' + vm.currentChat.id + '/message';
            vm.stompClient.send(vm.adress, {'session_id': $sessionStorage.sessionId}, JSON.stringify(vm.outMessage));
            vm.outMessage.message = "";
        }

        function removeUserFromChat(userId) {
            chatModel.removeUserFromChat(vm.currentChat.id, userId).then(
                function (success) {
                    vm.getActiveUsersInChat();
                });
        }

        function getActiveUsersInChat() {
            chatModel.getActiveUsersInChat(vm.currentChat.id).then(
                function (success) {
                    vm.usersInChat = success.data;
                });
        }

        function deleteChat(chatId) {
            chatModel.removeUserFromChat(chatId, $sessionStorage.userProfile.id).then(
                function (success) {
                    if (vm.currentChat.id === chatId) {
                        unSubscribe();
                        vm.messages = {};
                        vm.usersInChat = null;
                        vm.currentChat = {};
                        getChats();
                    }
                    else {
                        getChats();
                    }
                });
        }

        function addUserToChat(userId) {
            chatModel.addUserToChat(vm.currentChat.id, userId).then(
                function (success) {
                    vm.getActiveUsersInChat();
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