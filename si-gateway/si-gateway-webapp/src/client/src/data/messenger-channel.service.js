app.service('messengerChannelService', function ($sessionStorage, webSocketService) {

    var vm = this;
    vm.listOfChats = $sessionStorage.listOfChats;
    vm.listOfChatsCallbacks = [];
    vm.channelCallbacks = [];

    var destination = '/api/subscribe/chat/';
    vm.subscriptionsCallbacks = [];
    vm.subscribed = false;

    function registerListOfChatsObserver(callback) {
            vm.listOfChatsCallbacks.push(callback);
    }

    function registerChannelObserver(callback) {
        vm.channelCallbacks.push(callback);
    }

    function notifyChannelObservers(newChatData) {
        angular.forEach(vm.channelCallbacks, function (callback) {
            callback(newChatData);
        });
    }

    function notifyListOfChatObservers(chatData) {
        $sessionStorage.listOfChats = vm.listOfChats;
        angular.forEach(vm.listOfChatsCallbacks, function (callback) {
            callback(chatData);
        });
    }

    function updateListOfChats(newChatData) {
        var oldData = vm.listOfChats.find(function (item) {
            return item.id === newChatData.id;
        });
        if (oldData) {
            vm.listOfChats.splice(vm.listOfChats.indexOf(oldData), 1, newChatData);
        }
        else {
            vm.listOfChats.push(newChatData);
        }
        notifyListOfChatObservers(newChatData);
    }

    function removeChatFromList(chatData) {
        vm.listOfChats.splice(vm.listOfChats.indexOf(chatData), 1);
        notifyListOfChatObservers();
    }

    function findActiveChatWithUser(user) {
        return vm.listOfChats
            .filter(function (chat) {
                return chat.usersInChat.length === 1 && isUserActiveInChat(chat, user);
            });
    }

    function isUserActiveInChat(chat, user) {
        var res = chat.usersInChat.find(function (item) {
            return item.userId === user.userId;
        });
        return res !== undefined;
    }


    /**WebSocket subscription part*/
    function initWsSubscription() {
        if (!webSocketService.isConnectionReady()) {
            setTimeout(function () {
                initWsSubscription();
            }, 50);
        }
        else {
            _doSubscribeAll(destination);
        }
    }

    /**
     *  Subscribes all channels from vm.listOfChats
     */
    function _doSubscribeAll(destination) {
        vm.listOfChats.forEach(function (chat) {
            vm.subscriptionsCallbacks[chat.id] = (webSocketService.subscribe(destination + chat.id));
        });

        /**
         *  This should be called after getting status: connection is ready;
         *  in other way the subscription will happens twice on the same channel.
         */
        webSocketService.registerObserverCallback(initWsSubscription);
        vm.subscribed = true;

    }

    /**
     *  Passes callback function for incoming messages to existing subscription due channelId
     */
    function registerSubscriptionCallback(callback, channelId) {
        vm.subscriptionsCallbacks[channelId].then(null, null,
            function (message) {
                callback(message);
            });
    }

    /**
     *  Subscribes new channels with any ids
     */
    function subscribeNewChannel(channelId) {
        vm.subscribed = false;
        vm.subscriptionsCallbacks[channelId] = (webSocketService.subscribe(destination + channelId));
        _isSubscriptionSuccess(channelId);
    }

    function _isSubscriptionSuccess(channelId) {
        if (!vm.subscriptionsCallbacks[channelId]) {
            setTimeout(function () {
                _isSubscriptionSuccess();
            }, 50);
        }
        else {
            vm.subscribed = true;
        }
    }

    /**
     *  Checks if subscriptions are happened
     */
    function isSubscribed() {
        return vm.subscribed;
    }

    vm.registerListOfChatsObserver = registerListOfChatsObserver;
    vm.registerChannelObserver = registerChannelObserver;
    vm.notifyChannelObservers = notifyChannelObservers;
    vm.notifyListOfChatObservers = notifyListOfChatObservers;
    vm.updateListOfChats = updateListOfChats;
    vm.removeChatFromList = removeChatFromList;
    vm.findActiveChatWithUser = findActiveChatWithUser;

    vm.initWsSubscription = initWsSubscription;
    vm.registerSubscriptionCallback = registerSubscriptionCallback;
    vm.subscribeNewChannel = subscribeNewChannel;
    vm.isSubscribed = isSubscribed;
});