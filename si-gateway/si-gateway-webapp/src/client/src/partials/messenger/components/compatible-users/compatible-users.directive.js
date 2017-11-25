app.directive('compatibleUsersDirective', function ($state) {
    return {
        restrict: "E",
        scope: {
            users: '=',
            addChat:'&',
            addUserToChat:'&'
        },
        templateUrl: 'partials/messenger/components/compatible-users/compatible-users.html',

        link: function (scope, element, attrs) {

            scope.createChat = function (currUserId) {
                scope.addChat({userId:currUserId});
            };
            scope.addNewUserToChat = function (currUserId) {
                scope.addUserToChat({userId:currUserId});
            };
            scope.changeStateToUserSearch = function () {
                $state.go('userSearch');
            };
        }
    };
});