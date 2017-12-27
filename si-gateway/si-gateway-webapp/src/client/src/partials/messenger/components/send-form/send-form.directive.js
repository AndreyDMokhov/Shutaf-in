app.directive('sendFormDirective', function (webSocketService, messengerCurrentDataService) {
    return {
        restrict: "E",
        templateUrl: 'partials/messenger/components/send-form/send-form.html',
        scope: {},
        link: function (scope, element, attrs) {

            scope.outMessage = {};
            scope.currentChat = {};

            function init() {
                messengerCurrentDataService.registerCurrentChatObserver(updateCurrentChat);
            }

            function updateCurrentChat() {
                scope.currentChat = messengerCurrentDataService.currentChat;
            }

            scope.isEmpty = function () {
                return Object.keys(scope.currentChat).length === 0;
            };

            scope.sendMessageThrowWs = function () {
                if (!scope.outMessage.message || scope.outMessage.message === '') {
                    return;
                }
                scope.outMessage.messageType = 1;
                scope.address = '/api/chat/' + scope.currentChat.id + '/message';
                webSocketService.sendMessage(scope.outMessage, scope.address);
                scope.outMessage.message = "";
            };

            init();
        }
    };
});