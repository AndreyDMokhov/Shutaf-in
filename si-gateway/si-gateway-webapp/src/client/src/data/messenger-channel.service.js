app.service('messengerChannelService', function ($sessionStorage, webSocketService) {

    var vm = this;
    vm.listOfChats = $sessionStorage.listOfChats;
    vm.listOfChatsCallbacks = [];
    vm.channelCallbacks = [];

    var destination = '/api/subscribe/chat/';
    vm.initWsCallbacks = [];
    vm.channelMessagesCallbacks = [];
    vm.initChannelCallbacks = [];

    function registerListOfChatsObserver(callback) {
        vm.listOfChatsCallbacks.push(callback);
    }

    function registerChannelObserver(callback) {
        vm.channelCallbacks.push(callback);
    }

    function notifyChannelObservers(channel) {
        angular.forEach(vm.channelCallbacks, function (callback) {
            callback(channel);
        });
    }
    //TODO add handler for case if listOfChats is empty
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
    function initWsConnection() {
        if (!webSocketService.isConnectionReady()) {
            setTimeout(function () {
                initWsConnection();
            }, 100);
        }
        else {
            _doSubscribe(destination);
        }
    }

    function _doSubscribe(destination) {
        vm.listOfChats.forEach(function (chat) {
            vm.initWsCallbacks[chat.id] = (webSocketService.subscribe(destination + chat.id));
        });

        /**
         *  This should be called after getting status: connection is ready;
         *  in other way the subscription will happens twice on the same channel.
         */
        webSocketService.registerObserverCallback(initWsConnection);
        notifyInitChannelObservers();
    }

    function registerChannelMessagesCallbacks(callback, chatId) {
        vm.initWsCallbacks[chatId].then(null, null,
            function (message) {
                callback(message);
            });
    }

    function registerInitChannelObservers(callback) {
        vm.initChannelCallbacks.push(callback);
    }

    function notifyInitChannelObservers() {
        angular.forEach(vm.initChannelCallbacks, function (callback) {
            callback();
        });
    }

    vm.registerListOfChatsObserver = registerListOfChatsObserver;
    vm.registerChannelObserver = registerChannelObserver;
    vm.notifyChannelObservers = notifyChannelObservers;
    vm.notifyListOfChatObservers = notifyListOfChatObservers;
    vm.updateListOfChats = updateListOfChats;
    vm.removeChatFromList = removeChatFromList;
    vm.findActiveChatWithUser = findActiveChatWithUser;

    vm.initWsConnection = initWsConnection;
    vm.registerChannelMessagesCallbacks = registerChannelMessagesCallbacks;
    vm.registerInitChannelObservers = registerInitChannelObservers;
    vm.notifyInitChannelObservers = notifyInitChannelObservers;
});