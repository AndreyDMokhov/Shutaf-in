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

        activate();
    }
});
