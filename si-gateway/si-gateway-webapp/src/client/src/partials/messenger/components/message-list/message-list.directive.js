app.directive('messageListDirective', function (messengerCurrentDataService) {
    return {
        restrict: "E",
        scope: {},
        templateUrl: 'partials/messenger/components/message-list/message-list.html',

        link: function (scope, element, attrs) {

            scope.messages = [];

            function init() {
                messengerCurrentDataService.registerMessagesObserver(updateMessages);
            }

            var messageList = angular.element(element[0].children[0]);

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

            function markMessageItems() {
                angular.element(document).ready(function () {
                    for (var i = 0; i < scope.messages.length; i++) {
                        if (scope.messages[i].isNew) {
                            messageList[0].children[i].style.backgroundColor = '#C3C3C3';
                        }
                    }
                });

            }

            scope.unmarkMessageItem = function (index) {
                scope.messages[index].isNew = false;
                messageList[0].children[index].style.backgroundColor = '#FAFAFA';
            };

            init();
        }
    };
});