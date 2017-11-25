app.directive('chatUsersDirective', function () {
    return {
        restrict: "E",
        scope: {
            usersInChat: '=',
            removeUserFromChat:'&'
        },
        templateUrl: 'partials/messenger/components/chat-users/chat-users.html',
        link: function (scope, element, attrs) {

            scope.removeUser = function (currUserId) {
                scope.removeUserFromChat({userId:currUserId});
            };
        }
    };
});