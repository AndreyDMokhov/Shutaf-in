app.controller('chatController', function (chatModel, $sessionStorage, $state) {

//  TODO: add pop up window for chat add function
//  TODO: code clean up.
//  TODO: add translate
//  TODO: concat user names to chat name

        var vm = this;

        vm.users = {};
        vm.dataLoading = false;
        vm.chatName = null;
        vm.currentChat = {};
        vm.listOfChats = {};
        vm.messages = {};
        vm.usersInChat = {};
        vm.currentUserId = $sessionStorage.userProfile.userId;

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

        function addChat() {
            vm.dataLoading = true;
            vm.currentChat.id = null;
            chatModel.addChat(vm.chatName).then(
                function (success) {
                    // joinChat(success.data.data);
                    getChats();
                    vm.chatName = "";
                    vm.dataLoading = false;
                }, function (error) {
                    vm.dataLoading = false;
                });
        }

        function getChats() {
            chatModel.getChats().then(
                function (success) {
                    vm.listOfChats = success.data.data;
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
                    getActiveUsersInChat();
                });
        }

        function getActiveUsersInChat() {
            chatModel.getActiveUsersInChat(vm.currentChat.id).then(
                function (success) {
                    vm.usersInChat = success.data.data;
                });
        }

        function removeUserFromChat(userId) {
            chatModel.removeUserFromChat(vm.currentChat.id, userId).then(
                function (success) {
                    getActiveUsersInChat();
                });
        }


        function updateChatMessages(messages) {
            vm.messages = messages;
        }

        function updateChatData(chatData) {
            if (vm.currentChat.id === chatData.id) {
                return;
            }
            vm.currentChat = chatData;
            getActiveUsersInChat();
        }

        function changeStateToUserSearch(){
            $state.go('userSearch');
        }


        activate();

        vm.getUserData = getUserData;
        vm.addChat = addChat;
        vm.getChats = getChats;
        vm.removeChat = removeChat;
        vm.addUserToChat = addUserToChat;
        vm.getActiveUsersInChat = getActiveUsersInChat;
        vm.removeUserFromChat = removeUserFromChat;
        vm.updateChatMessages = updateChatMessages;
        vm.updateChatData = updateChatData;
        vm.changeStateToUserSearch = changeStateToUserSearch;
    }
);
