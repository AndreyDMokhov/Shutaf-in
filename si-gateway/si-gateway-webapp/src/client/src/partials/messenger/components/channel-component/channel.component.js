app.component('channelComponent', {
    templateUrl: 'partials/messenger/components/channel-component/channel.component.html',
    bindings: {},
    controllerAs: 'vm',

    controller: function (messengerModel, $sessionStorage, $filter, messengerChannelService, userSearchModel, messengerManagementService) {

        var vm = this;

        vm.listOfChats = [];
        vm.userIdsInChat = [];

        function activate() {
            messengerChannelService.registerListOfChatsObserver(updateListOfChats);
            angular.element(document).ready(function () {
                messengerManagementService.activateMessenger();
            });
        }

        function updateListOfChats(newChatData) {
            vm.listOfChats = messengerChannelService.listOfChats;
            cacheMessengerUsersData();
            if (!newChatData) {
                checkChatTitlesList();
            }
            else {
                checkOneChatTitle(newChatData);
            }
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

        function cacheMessengerUsersData() {
            vm.userIdsInChat = [];
            angular.forEach(vm.listOfChats, function (chatItem) {
                _findAndSaveUsersDataToStorage(chatItem.usersInChat);
            });
            _deleteRedundantDataInCache();
            /**
             * Update data in sessionStorage after deleting old user data
             */
            $sessionStorage.userIdsInChat = vm.userIdsInChat;
        }

        function _findAndSaveUsersDataToStorage(usersInChat) {
            angular.forEach(usersInChat, function (chatUser) {
                if (vm.userIdsInChat.indexOf(chatUser.userId) === -1) {
                    vm.userIdsInChat.push(chatUser.userId);
                    userSearchModel.getCompressedUserImageById(chatUser.userId).then(
                        function (success) {
                            $sessionStorage[chatUser.userId] = success.data.data;
                        }
                    );
                }
            });
        }

        function _deleteRedundantDataInCache() {
            if ($sessionStorage.userIdsInChat && $sessionStorage.userIdsInChat.length > 0) {
                angular.forEach($sessionStorage.userIdsInChat, function (userId) {
                    if (vm.userIdsInChat.indexOf(userId) === -1) {
                        delete $sessionStorage[userId];
                    }
                });
            }
        }

        activate();
    }
});
