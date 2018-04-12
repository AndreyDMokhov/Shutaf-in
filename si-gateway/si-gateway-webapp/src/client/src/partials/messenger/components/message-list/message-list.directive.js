app.directive('messageListDirective', function (messengerCurrentDataService) {
    return {
        restrict: "E",
        scope: {},
        templateUrl: 'partials/messenger/components/message-list/message-list.html',

        link: function (scope, element, attrs) {

            scope.messages = [];
            var messageList = angular.element(element[0].children[0]);

            function init() {
                messengerCurrentDataService.registerMessagesObserver(updateMessages);

            }

            scope.prepareChatWindow = function () {
                    scrollToBottom();
                    markMessageItems();
            };

            function scrollToBottom() {
                messageList.scrollTop(messageList.prop('scrollHeight'));
            }

            function updateMessages() {
                scope.messages = messengerCurrentDataService.messages;
            }


            scope.unmarkMessageItem = function (message) {
                message.isNew = false;
            };

            init();
        }
    };
});