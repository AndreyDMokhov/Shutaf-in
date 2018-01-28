angular.module('app').directive('userAvatar', function (Restangular,
                                                        $sessionStorage,
                                                        $uibModal,
                                                        userSearchModel,
                                                        $filter,
                                                        notify) {
    return {
        restrict: "E",
        template: '<img ng-click="openModalImageSize()" ng-src={{getUserImagePath()}} class="logo-center pointer" width="{{width}}" height="{{height}}">',
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
            scope.originImage = {};
            var DEFAULT_IMAGE_PATH = '../../images/default_avatar.png';
            var BASE64_IMAGE_PATH = 'data:image/jpeg;base64,';

            scope.getUserImagePath = function () {
                if (!scope.userId) {
                    return scope.image = DEFAULT_IMAGE_PATH;
                }
                scope.image = findUserImage();
                if (!scope.image) {
                    return scope.image = DEFAULT_IMAGE_PATH;
                }
                else {
                    return scope.image = BASE64_IMAGE_PATH + scope.image;
                }
            };

            function findUserImage() {
                if ($sessionStorage.userProfile.userId === parseInt(scope.userId)) {
                    scope.currentUser = $sessionStorage.userProfile;
                    return scope.currentUser.userImage;
                }
                if ($sessionStorage[scope.userId]) {
                    scope.currentUser = $sessionStorage[scope.userId];
                    return $sessionStorage[scope.userId].image;
                }
                // console.log(scope.userId);
                var bl='';
                    return userSearchModel.getCompressedUserImageById(scope.userId).then(
                        function (success) {
                            console.log(scope.userId);
                            console.log(success);
                            return success.data.data;
                        }

                    );
                return bl;
            }

            scope.open = function (size, parentSelector) {
                var modalInstance = $uibModal.open({
                    animation: scope.animationsEnabled,
                    templateUrl: 'data/user-avatar.modal.html',
                    controller: function ($uibModalInstance) {
                        var vm = this;
                        vm.currentImage = scope.originImage;
                        vm.fullName = scope.currentUser.firstName + " " + scope.currentUser.lastName;

                        vm.closeModal = function () {
                            $uibModalInstance.close();
                        };
                    },
                    controllerAs: 'vm',
                    size: 'lg'
                });
            };

            scope.openModalImageSize = function () {
                if (scope.image === DEFAULT_IMAGE_PATH) {
                    scope.originImage = scope.image;
                    scope.open();
                }
                else {
                    userSearchModel.getOriginalUserImageById(scope.userId).then(
                        function (success) {
                            scope.originImage = 'data:image/jpeg;base64,' + success.data.data.image;
                            scope.open();
                        },
                        function (error) {
                            if (error === undefined || error === null) {
                                notify.set($filter('translate')('Error.SYS'), {type: 'error'});
                            }
                            notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                        }
                    );
                }
            };

        }
    };
});