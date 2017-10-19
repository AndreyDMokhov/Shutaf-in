app.directive('messageList', function () {
    return {
        restrict: "E",
        scope: {
            messages: '='
        },
        templateUrl: 'partials/chat/components/message-list/message-list.html',

        link: function (scope, element, attrs) {

            //TODO: is there a better way to get body properties?
            //TODO: replace $watch
            var body = angular.element(element[0].children[0]);

            var prepareChatWindow = function () {
                angular.element(document).ready(function () {
                    scrollToBottom();
                    markMessageItems();
                });
            };

            var scrollToBottom = function () {
                body.scrollTop(body.prop('scrollHeight'));
            };

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
                        body[0].children[i].style.backgroundColor = '#C3C3C3';
                    }
                }
            }

            scope.unmarkMessageItem = function (index) {
                scope.messages[index].isNew = false;
                body[0].children[index].style.backgroundColor = '#FAFAFA';
            }

        }
    };
});