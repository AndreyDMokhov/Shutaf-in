app.service('messengerCurrentDataService', function () {

    var vm = this;

    /**Part for currentChat data*/
    /**START*/
    vm.currentChat = {};
    vm.currentChatCallbacks = [];

    function registerCurrentChatObserver(callback) {
        vm.currentChatCallbacks.push(callback);
    }

    function setCurrentChat(newChatData) {
        vm.currentChat = newChatData;
        notifyObservers(vm.currentChatCallbacks);
    }

    function removeCurrentChat() {
        vm.currentChat = {};
        notifyObservers(vm.currentChatCallbacks);
        removeMessages();
    }

    /**END*/


    /**Part for messages data*/
    /**START*/
    vm.messages = [];
    vm.messagesCallbacks = [];

    function registerMessagesObserver(callback) {
        vm.messagesCallbacks.push(callback);
    }

    function setMessages(messages) {
        vm.messages = messages;
        notifyObservers(vm.messagesCallbacks);
    }

    function removeMessages() {
        vm.messages = [];
        notifyObservers(vm.messagesCallbacks);
    }

    /**END*/

    function isUserActiveInCurrentChat(user) {
        if(!vm.currentChat.usersInChat){
            return false;
        }
        var res = vm.currentChat.usersInChat.find(function (item) {
            return item.userId === user.userId;
        });
        return res !== undefined;
    }

    function notifyObservers(callbacksArr) {
        angular.forEach(callbacksArr, function (callback) {
            callback();
        });
    }

    vm.setCurrentChat = setCurrentChat;
    vm.registerCurrentChatObserver = registerCurrentChatObserver;
    vm.removeCurrentChat = removeCurrentChat;

    vm.registerMessagesObserver = registerMessagesObserver;
    vm.setMessages = setMessages;
    vm.removeMessages = removeMessages;

    vm.isUserActiveInCurrentChat = isUserActiveInCurrentChat;
});