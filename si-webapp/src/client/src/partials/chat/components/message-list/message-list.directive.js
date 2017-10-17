app.directive('messageList', function () {
    return {
        restrict: "E",
        scope: {
            messages: '='
        },
        templateUrl: 'partials/chat/components/message-list/message-list.html',

        link: function (scope, element, attrs) {

            scope.innerMessages = scope.messages;
            var body = angular.element(element[0].children[0]);

            var prepareChatWindow = function () {
                angular.element(document).ready(function () {
                    scrollToBottom();
                    setTimeout(function () {
                        unmarkMessages();
                    }, 1000);
                });
            };

            var scrollToBottom = function(){
                body.scrollTop(body.prop('scrollHeight'));
            };

            scope.$watchCollection('messages', function (val) {
                if (Object.keys(val).length === 0) {
                    scope.innerMessages = scope.messages;
                    return;
                }
                if (val) {
                    prepareChatWindow();
                    scope.innerMessages = scope.messages;
                }
            });

            function unmarkMessages(){
                for (var i = 0; i < scope.messages.length; i++) {
                    scope.messages[i].isNew=false;
                }
                scope.innerMessages = scope.messages;
                scope.$apply();
            }
        }
    };
});