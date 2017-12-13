app.directive('compatibleUsersDirective', function ($state, messengerManagementService) {
    return {
        restrict: "E",
        scope: {
            users: '='
        },
        templateUrl: 'partials/messenger/components/compatible-users/compatible-users.html',

        link: function (scope, element, attrs) {

            scope.createChat = function (user) {
                messengerManagementService.sendMessage(user);
            };
            scope.addNewUserToChat = function (user) {
                messengerManagementService.addUserToChat(user);
            };
            scope.changeStateToUserSearch = function () {
                $state.go('userSearch');
            };
        }
    };
});