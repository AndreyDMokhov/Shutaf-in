/**
 * Contains channel component, message list and send form directives, and users directives (from matching and current chat users)
 */
app.component('messengerUiComponent', {
    templateUrl: 'partials/messenger/messenger-ui.component.html',
    bindings: {},
    controllerAs: 'vm',

    controller: function (messengerModel, $sessionStorage) {

        var vm = this;

        vm.users = [];
        vm.currentChat = {};
        vm.messages = [];
        vm.usersInChat = [];
        vm.characterLimit = 15;

        vm.channelProcessingCtrl = {};

        function activate() {
            getUserData();
        }

        function getUserData() {
            messengerModel.getUsers().then(
                function (success) {
                    vm.users = success.data.data;
                    $sessionStorage.users = vm.users;
                });
        }

        function addChat(userId) {
            if (!userId) {
                return;
            }
            vm.chatName = null;
            messengerModel.addChat(vm.chatName, userId).then(
                function (success) {
                    vm.currentChat = success.data.data;
                    vm.messages = [];
                    vm.usersInChat = [];
                    vm.usersInChat.push(findUserInUserListById(userId));
                    vm.channelProcessingCtrl.activateChat(vm.usersInChat, vm.currentChat);
                }
            );
        }

        function addUserToChat(userId) {
            if (!vm.currentChat.id || !userId || isUserActiveInCurrentChat(userId)) {
                return;
            }
            messengerModel.addUserToChat(vm.currentChat.id, userId).then(
                function (success) {
                    vm.usersInChat.push(findUserInUserListById(userId));
                    vm.channelProcessingCtrl.checkOneChatTitle(vm.usersInChat, vm.currentChat);
                });
        }

        function isUserActiveInCurrentChat(userId) {
            var res = vm.usersInChat.find(function (item) {
                return item.userId === userId;
            });
            return res !== undefined;
        }

        function findUserInUserListById(userId) {
            return vm.users.find(function (item) {
                return item.userId === userId;
            });
        }

        function removeUserFromChat(userId) {
            messengerModel.removeUserFromChat(vm.currentChat.id, userId).then(
                function (success) {
                    vm.usersInChat = vm.usersInChat.filter(function (item) {
                        return item.userId !== userId;
                    });
                    vm.channelProcessingCtrl.checkOneChatTitle(vm.usersInChat, vm.currentChat);
                });
        }

        function getActiveUsersInChat() {
            messengerModel.getActiveUsersInChat(vm.currentChat.id).then(
                function (success) {
                    vm.usersInChat = success.data.data;
                });
        }

        function updateCurrentChatRoom(chatData) {
            vm.currentChat = chatData;
            getActiveUsersInChat();
        }

        function updateChatMessages(messages){
            vm.messages = messages;
        }

        function removeChat() {
            vm.currentChat = {};
            vm.messages.length=0;
            vm.usersInChat.length=0;
        }

        activate();

        vm.getUserData = getUserData;
        vm.addChat = addChat;
        vm.removeChat = removeChat;
        vm.addUserToChat = addUserToChat;
        vm.getActiveUsersInChat = getActiveUsersInChat;
        vm.removeUserFromChat = removeUserFromChat;
        vm.updateCurrentChatRoom = updateCurrentChatRoom;
        vm.updateChatMessages=updateChatMessages;
    }
});