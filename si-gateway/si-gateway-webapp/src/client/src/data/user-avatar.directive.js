angular.module('app').directive('userAvatar', function (Restangular, $sessionStorage, ngDialog) {
    return {
        restrict: "E",
        template: '<img ng-click="getUserDataAndShowPopup()" ng-src={{getUserImage()}} class="logo-center pointer" width="{{width}}" height="{{height}}">',
        scope: {
            userId: "@",
            width: "@",
            height: '@'
        },
        link: function (scope, element, attrs) {

            var rest = Restangular.withConfig(function (RestangularProvider) {
                RestangularProvider.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
            });

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

            scope.getUserDataAndShowPopup = function () {
                if (!scope.currentUser.genderId || !scope.currentUser.cityId || !scope.currentUser.dateOfBirth) {
                    rest.one('/api/users/search/' + scope.currentUser.userId).customGET().then(
                        function (success) {
                            var user = success.data;
                            scope.currentUser.genderId = user.genderId;
                            scope.currentUser.cityId = user.cityId;
                            scope.currentUser.dateOfBirth = user.dateOfBirth;
                            scope.showImagePopup();
                        });
                } else {
                    scope.showImagePopup();
                }
            };

            scope.showImagePopup = function () {
                scope.cities = $sessionStorage.cities;
                scope.genders = $sessionStorage.genders;
                ngDialog.open({
                    templateUrl: 'data/user-card.popup.html',
                    scope: scope,
                    showClose: false,
                    className: 'ngdialog-theme-default',
                    closeByDocument: true
                });
            };
        }
    };
});