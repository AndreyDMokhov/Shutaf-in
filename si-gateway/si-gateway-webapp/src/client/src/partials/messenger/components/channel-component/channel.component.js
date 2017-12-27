app.component('channelComponent', {
    templateUrl: 'partials/messenger/components/channel-component/channel.component.html',
    bindings: {},
    controllerAs: 'vm',

    controller: function (messengerModel, $sessionStorage, $filter, messengerChannelService, userSearchModel, messengerManagementService) {

        var vm = this;

        vm.listOfChats = [];

        function activate() {
            messengerChannelService.registerListOfChatsObserver(updateListOfChats);
            angular.element(document).ready(function () {
                        messengerManagementService.activateMessenger();
                    });
        }

        function updateListOfChats(newChatData) {
            vm.listOfChats = messengerChannelService.listOfChats;
            if (!newChatData) {
                checkChatTitlesList();
                angular.forEach(vm.listOfChats, function (item) {
                    findAndSaveUserImagesToStorage(item.usersInChat);
                });
            }
            else {
                checkOneChatTitle(newChatData);
                findAndSaveUserImagesToStorage(newChatData.usersInChat);
            }
        }

        function findAndSaveUserImagesToStorage(usersInChat) {
            angular.forEach(usersInChat, function (item) {
                if (!$sessionStorage[item.userId]) {
                    userSearchModel.getUserImageById(item.userId).then(
                        function (success) {
                            var data = success.data.data;
                            if (data.image) {
                                $sessionStorage[item.userId] =data.image;
                            }
                        }
                    );
                }
            });
        }

        function checkChatTitlesList() {
            vm.listOfChats.forEach(function (element) {
                if (element.hasNoTitle) {
                    setChatTitle(element);
                }
            });
        }

        function checkOneChatTitle(chatData) {
            if (chatData.hasNoTitle) {
                setChatTitle(chatData);
            }
        }

        function setChatTitle(chatData) {
            chatData.chatTitle = $filter('translate')('Chat.name.prefix');
            var fullChatTitle = [];
            chatData.usersInChat.forEach(function (elem) {
                fullChatTitle = fullChatTitle + ', ' + elem.firstName + ' ' + elem.lastName;
            });
            chatData.chatTitle = chatData.chatTitle + fullChatTitle;
        }

        activate();
    }
});
