app.service('messengerChannelService', function (webSocketService) {

    var vm = this;
    vm.listOfChats = [];
    vm.listOfChatsCallbacks = [];
    vm.channelActivateCallbacks = [];
    vm.initCallbacks = [];

    vm.destination = '/api/subscribe/chat/';
    vm.subscriptionsCallbacks = [];
    vm.subscribed = false;

    function registerListOfChatsObserver(callback) {
        vm.listOfChatsCallbacks.push(callback);
    }

    function notifyListOfChatObservers(chatData) {
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
        checkSubscriptions(newChatData);
        notifyListOfChatObservers(newChatData);
    }

    function removeChatFromList(chatData) {
        vm.listOfChats.splice(vm.listOfChats.indexOf(chatData), 1);
        notifyListOfChatObservers();
    }

    function registerChannelActivateObserver(callback) {
        vm.channelActivateCallbacks.push(callback);
    }

    function notifyChannelActivateObservers(chatData) {
        angular.forEach(vm.channelActivateCallbacks, function (callback) {
            callback(chatData);
        });
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

    function checkSubscriptions(chatData) {
        if (!webSocketService.subscriptionsList[vm.destination + chatData.id]) {
            subscribeNewChannel(chatData);
        }
    }


    /**WebSocket subscription part*/
    function initWsSubscription() {
        if (!webSocketService.isConnectionReady()) {
            setTimeout(function () {
                initWsSubscription();
            }, 50);
        }
        else {
            _doSubscribeAll(vm.destination);
        }
    }

    /**
     *  Subscribes all chats from vm.listOfChats
     */
    function _doSubscribeAll(destination) {
        vm.listOfChats.forEach(function (chatData) {
            if (chatData.isActiveUser) {
                vm.subscriptionsCallbacks[chatData.id] = (webSocketService.subscribe(destination + chatData.id));
            }
        });

        /**
         *  This should be called after getting status: connection is ready;
         *  in other way the subscription will happens twice on the same channel.
         */
        webSocketService.registerObserverCallback(initWsSubscription);
        vm.subscribed = true;
    }

    /**
     *  Passes callback function for incoming messages to existing subscription due chatId
     */
    function registerSubscriptionCallback(callback, chatData) {
        if (chatData.isActiveUser) {
            vm.subscriptionsCallbacks[chatData.id].then(null, null,
                function (message) {
                    callback(message);
                });
        }
    }

    /**
     *  Subscribes new chat.
     */
    function subscribeNewChannel(chatData) {
        if (chatData.isActiveUser) {
            vm.subscribed = false;
            vm.subscriptionsCallbacks[chatData.id] = (webSocketService.subscribe(vm.destination + chatData.id));
            notifyChannelActivateObservers(chatData);
            vm.subscribed = true;
        }
    }

    function unSubscribe(chatData) {
        webSocketService.unSubscribe(vm.destination + chatData.id);
    }

    /**
     *  Checks if subscriptions are happened
     */
    function isSubscribed() {
        return vm.subscribed;
    }

    vm.registerListOfChatsObserver = registerListOfChatsObserver;
    vm.registerChannelActivateObserver = registerChannelActivateObserver;
    vm.notifyChannelActivateObservers = notifyChannelActivateObservers;
    vm.notifyListOfChatObservers = notifyListOfChatObservers;
    vm.updateListOfChats = updateListOfChats;
    vm.removeChatFromList = removeChatFromList;
    vm.findActiveChatWithUser = findActiveChatWithUser;

    vm.initWsSubscription = initWsSubscription;
    vm.registerSubscriptionCallback = registerSubscriptionCallback;
    vm.subscribeNewChannel = subscribeNewChannel;
    vm.unSubscribe = unSubscribe;
    vm.isSubscribed = isSubscribed;
});