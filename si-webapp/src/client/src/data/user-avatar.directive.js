app.directive('userAvatar', function ($sessionStorage) {
    return {
        restrict: "E",
        template: '<img ng-src={{getUserImage()}} class="logo-center pointer" width="{{width}}" height="{{height}}">',
        scope: {
            userId: "@",
            width: "@",
            height: '@'
        },
        link: function (scope, element, attrs) {

            scope.image = '';
            scope.currentUser = {};

            scope.getUserImage = function() {
                if (!scope.userId) {
                    return scope.image = '../../images/default_avatar.png';
                }
                scope.image = findUserImage();
                if (!scope.image) {
                    return scope.image = '../../images/default_avatar.png';
                }
                else {
                    return scope.image = 'data:image/jpeg;base64,' + scope.image;
                }
            };

            function findUserImage() {
                if ($sessionStorage.userProfile.userId === parseInt(scope.userId)) {
                    scope.currentUser = $sessionStorage.userProfile;
                    return $sessionStorage.userProfile.userImage;
                } else {
                    var users = $sessionStorage.users;
                    var res = users.find(function (item) {
                        return item.userId === parseInt(scope.userId);
                    });
                    scope.currentUser = res;
                    return res.profileImage;
                }
            }
        }
    };
});
