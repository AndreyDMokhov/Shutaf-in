app.directive('sendMessageDirective', function ($state) {
    return {
        restrict: "E",
        template: '<button ng-click="sendMessage()" class="btn btn-primary button-size-font">' +
        '{{"Search.sendMessage.button" | translate}}</button>',
        scope: {
            user: "="
        },
        link: function (scope, element, attrs) {

            scope.sendMessage = function(){
                $state.go('chat', {user:scope.user});
            }
        }
    };
});