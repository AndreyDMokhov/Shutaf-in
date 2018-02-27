angular.module('app').directive('userAvatar', function ($uibModal,
                                                        userSearchModel,
                                                        $filter,
                                                        notify,
                                                        $sessionStorage) {
    return {
        restrict: "E",
        template: '<img ng-click="openModalImageSize()" ng-src={{image}} class="logo-center pointer" width="{{width}}" height="{{height}}">',
        scope: {
            userId: "@",
            width: "@",
            height: '@'
        },
        link: function (scope, element, attrs) {
            scope.image = '';
            scope.currentUser = {};
            scope.originImage = {};
            var DEFAULT_IMAGE_PATH = '../../images/default_avatar.png';
            var BASE64_IMAGE_PATH = 'data:image/jpeg;base64,';

            findUserImage();

            function findUserImage() {
                if (!scope.userId) {
                    scope.image = DEFAULT_IMAGE_PATH;
                }
                else if ($sessionStorage.userProfile.userId == scope.userId && $sessionStorage.userProfile.userImage) {
                    scope.image = BASE64_IMAGE_PATH + $sessionStorage.userProfile.userImage;
                }
                else if ($sessionStorage[scope.userId] && $sessionStorage[scope.userId].image) {
                    scope.image = BASE64_IMAGE_PATH + $sessionStorage[scope.userId].image;
                }
                else {
                    userSearchModel.getCompressedUserImageById(scope.userId).then(
                        function (success) {
                            scope.currentUser.firstName = success.data.data.firstName;
                            scope.currentUser.lastName = success.data.data.lastName;
                            if (success.data.data.image !== null) {
                                scope.image = BASE64_IMAGE_PATH + success.data.data.image;
                            }
                            else {
                                scope.image = DEFAULT_IMAGE_PATH;
                            }
                        },
                        function (error) {
                            scope.image = DEFAULT_IMAGE_PATH;
                        }
                    );
                }
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