app.service('messengerManagementService', function (messengerModel, messengerChannelService, messengerCurrentDataService) {

    var vm = this;

    function activateMessenger() {
        messengerChannelService.notifyListOfChatObservers();
        if (messengerCurrentDataService.currentChat.id) {
            activateChannel(messengerCurrentDataService.currentChat);
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
                messengerChannelService.subscribeNewChannel(vm.currentChat.id);
                messengerCurrentDataService.setCurrentChat(vm.currentChat);
                messengerCurrentDataService.removeMessages();
                messengerChannelService.updateListOfChats(vm.currentChat);
            }
        );
    }

    function activateChannel(chat) {
        messengerCurrentDataService.setCurrentChat(chat);
        messengerChannelService.notifyChannelObservers(chat);
    }

    function renameChat(chat, chatTitle) {
        messengerModel.renameChat(chat.id, chatTitle).then(
            function (success) {
                messengerChannelService.updateListOfChats(success.data.data);
            }
        );
    }

    function removeChat(chat) {
        if (!chat) {
            return;
        }
        messengerModel.removeChat(chat.id).then(
            function (success) {
                if (messengerCurrentDataService.currentChat.id === chat.id) {
                    messengerCurrentDataService.removeCurrentChat();
                }
                messengerChannelService.removeChatFromList(chat);
            });
    }

    function addUserToChat(userData) {
        if (!userData || messengerCurrentDataService.isUserActiveInCurrentChat(userData)) {
            return;
        }
        vm.currentChat = messengerCurrentDataService.currentChat;
        messengerModel.addUserToChat(vm.currentChat.id, userData.userId).then(
            function (success) {
                vm.currentChat.usersInChat.push(userData);
                messengerCurrentDataService.setCurrentChat(vm.currentChat);
                messengerChannelService.updateListOfChats(vm.currentChat);
            });
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
    vm.removeChat = removeChat;
    vm.renameChat = renameChat;
    vm.addUserToChat = addUserToChat;
    vm.removeUserFromChat = removeUserFromChat;

});