app.directive('sendMessageDirective', function ($state, messengerManagementService) {
    return {
        restrict: "E",
        template: '<button ng-click="sendMessage()" class="btn btn-primary button-size-font">' +
        '{{"Search.sendMessage.button" | translate}}</button>',
        scope: {
            user: "="
        },
        link: function (scope, element, attrs) {

            scope.sendMessage = function () {
                $state.go('chat');
                messengerManagementService.sendMessage(scope.user);
            };
        }
    };
});