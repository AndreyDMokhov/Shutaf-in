app.directive('chatUsersDirective', function (messengerCurrentDataService, messengerManagementService) {
    return {
        restrict: "E",
        scope: {},
        templateUrl: 'partials/messenger/components/chat-users/chat-users.html',
        link: function (scope, element, attrs) {

            scope.ussersInChat=[];

            function init() {
                messengerCurrentDataService.registerCurrentChatObserver(updateUsersInChat);
            }

            function updateUsersInChat() {
                scope.usersInChat = messengerCurrentDataService.currentChat.usersInChat;
            }

            scope.removeUser = function (user) {
                messengerManagementService.removeUserFromChat(user);
            };

            init();
        }
    };
});