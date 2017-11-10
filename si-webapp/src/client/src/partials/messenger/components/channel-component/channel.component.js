app.component('channelComponent', {
    templateUrl: 'partials/messenger/components/channel-component/channel.component.html',
    bindings: {},
    controllerAs: 'vm',
    /*todo transform this into a service*/
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

        function updateCurrentChatRoom(chatData) {
            if (vm.currentChat.id === chatData.id) {
                return;
            }
            vm.currentChat = chatData;
            vm.messengerUiCtrl.updateCurrentChatRoom(chatData);
        }
        function updateChatMessages(messages){
            vm.messengerUiCtrl.updateChatMessages(messages);
        }

        function activateChat(usersInChat, chatData){
            vm.listOfChats.push(chatData);
            vm.currentChat = chatData;
            checkOneChatTitle(usersInChat, chatData);
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
                if (element.hasNoTitle) {
                    getUsersInChatAndSetChatTitle(element);
                }
            });
        }

        function checkOneChatTitle(usersInChat, chatData) {
            if (chatData.hasNoTitle) {
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
            //todo get rig of this if else
            if (languageService.getUserLanguage().id === 2) {
                //todo substitute with translate filter
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
        vm.renameChat = renameChat;
        vm.checkOneChatTitle=checkOneChatTitle;
        vm.activateChat=activateChat;
        vm.updateCurrentChatRoom = updateCurrentChatRoom;
        vm.updateChatMessages=updateChatMessages;
    }
});
