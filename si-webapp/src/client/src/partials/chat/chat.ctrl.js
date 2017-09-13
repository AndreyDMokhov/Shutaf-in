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
        vm.subscribing = false;

        function activate() {
            getUserData();
            getChats();
        }

        function getUserData() {
            chatModel.getUsers().then(
                function (success) {
                    vm.users = success.data.data;
                });
        }

        function subscribe() {
            vm.subscribing = true;
            if (!webSocketService.isConnectionReady()) {
                setTimeout(function () {
                    subscribe();
                }, 1000);
            }
            else {
                vm.subscribing = false;
                _doSubscribe();
            }
        }

        function _doSubscribe() {
            webSocketService.subscribe('/subscribe/chat/' + vm.currentChat.id).then(null,null,
                function(message) {
                    showChatMessage(message);
                });
        }

        function showChatMessage(message) {
            vm.messages.push(message);
        }

        function addChat() {
            vm.dataLoading = true;
            vm.currentChat.id = null;
            chatModel.addChat(vm.chatName).then(
                function (success) {
                    joinChat(success.data.data);
                    getChats();
                    vm.chatName = "";
                }, function (error) {
                    vm.dataLoading = false;
                })
        }

        function getChats() {
            chatModel.getChats().then(
                function (success) {
                    vm.listOfChats = success.data.data;
                })
        }

        function joinChat(chat) {
            if (vm.currentChat.id === chat.id) {
                return;
            }
            vm.currentChat = chat;
            subscribe();
            getAllMessages();
            getActiveUsersInChat();
        }

        function getAllMessages() {
            chatModel.getAllMessages(vm.currentChat.id).then(
                function (success) {
                    vm.messages = success.data.data;
                });
        }

        function sendMessage() {
            if (!vm.outMessage.message || vm.outMessage.message === '') {
                return;
            }
            vm.outMessage.messageType = 1;
            vm.address = '/chat/' + vm.currentChat.id + '/message';
            webSocketService.sendMessage(vm.outMessage, vm.address);
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
                    vm.usersInChat = success.data.data;
                });
        }

        function removeChat(chatId) {
            chatModel.removeChat(chatId).then(
                function (success) {
                    if (vm.currentChat.id === chatId) {
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
        vm.removeChat = removeChat;
        vm.addUserToChat = addUserToChat;
        vm.exitChat = exitChat;
    }
);