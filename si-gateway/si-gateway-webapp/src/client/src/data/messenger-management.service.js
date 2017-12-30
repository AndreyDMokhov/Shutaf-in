app.service('messengerManagementService', function ($sessionStorage, messengerModel,
                                                    messengerChannelService, messengerCurrentDataService) {

    var vm = this;

    function activateMessenger() {
        if (messengerChannelService.listOfChats.length < 1) {
            setTimeout(function () {
                activateMessenger();
            }, 50);
        } else {
            messengerChannelService.notifyListOfChatObservers();
            if (messengerCurrentDataService.currentChat.id) {
                activateChannel(messengerCurrentDataService.currentChat);
            }
        }
    }

    function sendMessage(userData) {
        if (!userData || !userData.userId) {
            return;
        }
        var activeChat = messengerChannelService.findActiveChatWithUser(userData);
        /** Checks criteria for opening existing chat with user*/
        if (activeChat && activeChat.length > 0) {
            activateChannel(activeChat[0]);
        } else {
            addChat(userData);
        }
    }

    function addChat(userData) {
        vm.chatName = null;
        messengerModel.addChat(vm.chatName, userData.userId).then(
            function (success) {
                vm.currentChat = success.data.data;
                messengerCurrentDataService.setCurrentChat(vm.currentChat);
                messengerChannelService.updateListOfChats(vm.currentChat);
                messengerCurrentDataService.removeMessages();
            }
        );
    }

    function activateChannel(chatData) {
        messengerCurrentDataService.setCurrentChat(chatData);
        messengerChannelService.notifyChannelActivateObservers(chatData);
    }

    function renameChat(chatData, chatTitle) {
        messengerModel.renameChat(chatData.id, chatTitle).then(
            function (success) {
                messengerChannelService.updateListOfChats(success.data.data);
            }
        );
    }

    function deleteChat(chatData) {
        if (!chatData) {
            return;
        }
        messengerModel.deleteChat(chatData.id).then(
            function (success) {
                if (messengerCurrentDataService.currentChat.id === chatData.id) {
                    messengerCurrentDataService.removeCurrentChat();
                }
                messengerChannelService.unSubscribe(chatData);
                messengerChannelService.removeChatFromList(chatData);
            });
    }

    function addUserToChat(userData) {
        if (userData && Object.keys(messengerCurrentDataService.currentChat).length !== 0 &&
            !messengerCurrentDataService.isUserActiveInCurrentChat(userData) &&
            messengerCurrentDataService.currentChat.isActiveUser) {

            vm.currentChat = messengerCurrentDataService.currentChat;
            messengerModel.addUserToChat(vm.currentChat.id, userData.userId).then(
                function (success) {
                    vm.currentChat.usersInChat.push(userData);
                    messengerCurrentDataService.setCurrentChat(vm.currentChat);
                    messengerChannelService.updateListOfChats(vm.currentChat);
                });
        }
    }

    function removeUserFromChat(userData) {
        if (!userData || !messengerCurrentDataService.isUserActiveInCurrentChat(userData)) {
            return;
        }
        vm.currentChat = messengerCurrentDataService.currentChat;
        messengerModel.removeUserFromChat(vm.currentChat.id, userData.userId).then(
            function (success) {
                vm.currentChat.usersInChat.splice(vm.currentChat.usersInChat.indexOf(userData), 1);
                messengerCurrentDataService.setCurrentChat(vm.currentChat);
                messengerChannelService.updateListOfChats(vm.currentChat);
            });
    }

    vm.activateMessenger = activateMessenger;
    vm.sendMessage = sendMessage;
    vm.addChat = addChat;
    vm.deleteChat = deleteChat;
    vm.renameChat = renameChat;
    vm.addUserToChat = addUserToChat;
    vm.removeUserFromChat = removeUserFromChat;

});