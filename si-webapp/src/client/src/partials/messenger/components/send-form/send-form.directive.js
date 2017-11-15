app.directive('sendFormDirective', function (webSocketService) {
    return {
        restrict: "E",
        templateUrl: 'partials/messenger/components/send-form/send-form.html',
        scope: {
            chatData: '='
        },
        link: function(scope, element, attrs) {

            scope.outMessage = {};
            
            scope.isEmpty = function () {
                return Object.keys(scope.chatData).length === 0;
            };

            scope.sendMessageThrowWs = function () {
                if (!scope.outMessage.message || scope.outMessage.message === '') {
                    return;
                }
                scope.outMessage.messageType = 1;
                scope.address = '/api/chat/' + scope.chatData.id + '/message';
                webSocketService.sendMessage(scope.outMessage, scope.address);
                scope.outMessage.message = "";
            };
        }
    };
});