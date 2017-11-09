app.component('channelComponent', {
    templateUrl: 'partials/messenger/components/channel-component/channel.component.html',
    bindings: {},
    controllerAs: 'vm',
    require: {
        messengerUiCtrl: '^messengerUiComponent'
    },
    controller: function (messengerModel, $sessionStorage, $state, languageService) {

        var vm = this;

        vm.$onInit = function () {
            vm.messengerUiCtrl.channelProcessingCtrl = this;
        };
        vm.currentChat = {};
        vm.listOfChats = [];
        vm.currentUserId = $sessionStorage.userProfile.userId;

        function activate() {
            getChats();
        }

        function removeChat(chat) {
            if (!chat) {
                return;
            }
            messengerModel.removeChat(chat.id).then(
                function (success) {
                    if (vm.currentChat.id === chat.id) {
                        vm.currentChat = {};
                        vm.messengerUiCtrl.removeChat();
                    }
                    vm.listOfChats.splice(vm.listOfChats.indexOf(chat), 1);
                });
        }

        function renameChat(chatId, chatTitle) {
            messengerModel.renameChat(chatId, chatTitle).then(
                function (success) {
                    var newChatData = success.data.data;
                    var oldTitle = vm.listOfChats.find(function (item) {
                        return item.id === newChatData.id;
                    });
                    vm.listOfChats.splice(vm.listOfChats.indexOf(oldTitle), 1, newChatData);
                }
            );
        }

        function updateChatRoom(messages, chatData) {
            if (vm.currentChat.id === chatData.id) {
                return;
            }
            vm.currentChat.id = chatData.id;
            vm.messengerUiCtrl.updateChatRoom(messages, chatData);
        }

        function getChats() {
            messengerModel.getChats().then(
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
            messengerModel.getActiveUsersInChat(chatData.id).then(
                function (success) {
                    var usersInChat = success.data.data;
                    chatData = setChatTitle(usersInChat, chatData);
                }
            );
        }

        function setChatTitle(usersInChat, chatData) {
            if (languageService.getUserLanguage().id === 2) {
                chatData.chatTitle = 'Вы';
            }
            else {
                chatData.chatTitle = 'You';
            }
            var fullChatTitle = usersInChat.map(function (elem) {
                return elem.firstName + ' ' + elem.lastName;
            }).join(", ");
            chatData.chatTitle = chatData.chatTitle + ', ' + fullChatTitle;
        }


        activate();

        vm.getChats = getChats;
        vm.removeChat = removeChat;
        vm.updateChatRoom = updateChatRoom;
        vm.renameChat = renameChat;
        vm.checkOneChatTitle=checkOneChatTitle;
    }
});
