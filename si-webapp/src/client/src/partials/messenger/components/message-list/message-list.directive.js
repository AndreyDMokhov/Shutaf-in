app.directive('messageListDirective', function () {
    return {
        restrict: "E",
        scope: {
            messages: '='
        },
        templateUrl: 'partials/messenger/components/message-list/message-list.html',

        link: function (scope, element, attrs) {

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

            scope.$watchCollection('messages', function (val) {
                if (!val || Object.keys(val).length === 0) {
                    return;
                }
                if (val) {
                    prepareChatWindow();
                }
            });

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
        }
    };
});