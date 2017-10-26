app.controller('chatController', function (chatModel, $sessionStorage, $state, languageService, ngDialog) {

        var vm = this;

        vm.users = [];
        vm.currentChat = {};
        vm.listOfChats = [];
        vm.messages = [];
        vm.usersInChat = [];
        vm.currentUserId = $sessionStorage.userProfile.userId;
        vm.characterLimit = 15;

        function activate() {
            getUserData();
            getChats();
        }

        function getUserData() {
            chatModel.getUsers().then(
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
            chatModel.addChat(vm.chatName, userId).then(
                function (success) {
                    vm.currentChat = success.data.data;
                    vm.listOfChats.push(vm.currentChat);
                    vm.usersInChat = [];
                    vm.usersInChat.push(findUserInUserListById(userId));
                    checkOneChatTitle(vm.usersInChat, vm.currentChat);
                    vm.messages = [];
                }
            );
        }

        function removeChat(chat) {
            if(!chat){
                return;
            }
            chatModel.removeChat(chat.id).then(
                function (success) {
                    if (vm.currentChat.id === chat.id) {
                        vm.messages = [];
                        vm.usersInChat = [];
                        vm.currentChat = {};
                    }
                    vm.listOfChats.splice(vm.listOfChats.indexOf(chat),1);
                });
        }

        function renameChat(chatId, chatTitle) {
            chatModel.renameChat(chatId,chatTitle).then(
                function(success){
                    var newChatData = success.data.data;
                    var oldVal = vm.listOfChats.find(function (item) {
                        return item.id === newChatData.id;
                    });
                    vm.listOfChats.splice(vm.listOfChats.indexOf(oldVal), 1, newChatData);

                }
            )
        }

        function addUserToChat(userId) {
            if (!vm.currentChat.id || !userId || isUserActiveInCurrentChat(userId)) {
                return;
            }
            chatModel.addUserToChat(vm.currentChat.id, userId).then(
                function (success) {
                    vm.usersInChat.push(findUserInUserListById(userId));
                    checkOneChatTitle(vm.usersInChat, vm.currentChat);
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
            chatModel.removeUserFromChat(vm.currentChat.id, userId).then(
                function (success) {
                    vm.usersInChat = vm.usersInChat.filter(function (item) {
                        return item.userId !== userId;
                    });
                    checkOneChatTitle(vm.usersInChat, vm.currentChat);
                });
        }

        function getActiveUsersInChat() {
            chatModel.getActiveUsersInChat(vm.currentChat.id).then(
                function (success) {
                    vm.usersInChat = success.data.data;
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

        function changeStateToUserSearch() {
            $state.go('userSearch');
        }

        function getChats() {
            chatModel.getChats().then(
                function (success) {
                    vm.listOfChats = success.data.data;
                    checkChatTitlesList();
                });
        }

        function checkChatTitlesList() {
            vm.listOfChats.forEach(function (element) {
                if (element.isNoTitle) {
                    getUsersInChatAndSetChatTitle(element);
                }
            });
        }

        function checkOneChatTitle(usersInChat, chatData) {
            if (chatData.isNoTitle) {
                setChatTitle(usersInChat, chatData);
                if (chatData.id === vm.currentChat.id) {
                    vm.currentChat = chatData;
                }
            }
        }

        function getUsersInChatAndSetChatTitle(chatData) {
            chatModel.getActiveUsersInChat(chatData.id).then(
                function (success) {
                    var usersInChat = success.data.data;
                    setChatTitle(usersInChat, chatData);
                }
            );
        }

        function setChatTitle(usersInChat, chatData) {
            if(languageService.getUserLanguage().id===2){
                chatData.chatTitle='Вы';
            }
            else{
                chatData.chatTitle = 'You';
            }
            var fullChatTitle = usersInChat.map(function (elem) {
                return elem.firstName + ' ' + elem.lastName;
            }).join(", ");
            chatData.chatTitle = chatData.chatTitle +', '+ fullChatTitle;
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
        vm.renameChat=renameChat;
    }
);
