app.directive('messageListDirective', function (messengerCurrentDataService) {
    return {
        restrict: "E",
        scope: {
        },
        templateUrl: 'partials/messenger/components/message-list/message-list.html',

        link: function (scope, element, attrs) {

            scope.messages = [];

            function init() {
                messengerCurrentDataService.registerMessagesObserver(updateMessages);
            }

            var messageList = angular.element(element[0].children[0]);

            function prepareChatWindow() {
                angular.element(document).ready(function () {
                    scrollToBottom();
                    markMessageItems();
                });
            }

            function scrollToBottom() {
                messageList.scrollTop(messageList.prop('scrollHeight'));
            }

            function updateMessages() {
                scope.messages = messengerCurrentDataService.messages;
                if (scope.messages.length > 0) {
                    prepareChatWindow();
                }
            }

            function markMessageItems() {
                for (var i = 0; i < scope.messages.length; i++) {
                    if (scope.messages[i].isNew) {
                        messageList[0].children[i].style.backgroundColor = '#C3C3C3';
                    }
                }
            }

            scope.unmarkMessageItem = function (index) {
                scope.messages[index].isNew = false;
                messageList[0].children[index].style.backgroundColor = '#FAFAFA';
            };

            init();
        }
    };
});