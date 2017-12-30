app.service('notificationService', function (webSocketService, messengerChannelService, messengerCurrentDataService) {

    var vm = this;
    vm.destination = '/user/api/subscribe/notification';
    vm.notificationsList = [];
    vm.notificationsList["NEW_CHAT"] = _updateChat;
    vm.notificationsList["RENAME_CHAT"] = _updateChat;
    vm.notificationsList["ADD_CHAT_USER"] = _updateChat;
    vm.notificationsList["REMOVE_CHAT_USER"] = _updateChat;
    vm.notificationsList["DELETE_CHAT"] = _deleteChat;

    /**WebSocket subscription part*/
    function initWsSubscription() {
        if (!webSocketService.isConnectionReady()) {
            setTimeout(function () {
                initWsSubscription();
            }, 50);
        }
        else {
            _doSubscribe(vm.destination);
        }
    }

    function _doSubscribe(destination) {
        webSocketService.subscribe(destination).then(null, null,
            function (notification) {
                _executeNotification(notification);
            });

        /**
         *  This should be called after getting status: connection is ready;
         *  in other way the subscription will happens twice on the same channel.
         */
        webSocketService.registerObserverCallback(initWsSubscription);
    }

    function _executeNotification(notification) {
        vm.notificationsList[notification.notificationReason](notification.chatWithUsersListDTO);
    }

    function _updateChat(chatData) {
        messengerChannelService.updateListOfChats(chatData);
        if (messengerCurrentDataService.currentChat.id === chatData.id) {
            messengerCurrentDataService.setCurrentChat(chatData);
        }
    }

    function _deleteChat(chatData) {
        messengerChannelService.unSubscribe(chatData);
        _updateChat(chatData);
    }

    vm.initWsSubscription = initWsSubscription;
});