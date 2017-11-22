app.directive('modalWindowFullImage', function ($uibModal, $log, userSearchModel, $filter, notify) {
    return {
        restrict: "E",
        template: ' <img ng-src="{{getImage()}}" width="100px" height="100px" ng-click="openModalWindowFullImageSize()">',
        scope: {
            userProfile: "="
        },
        link: function (scope, element, attrs) {

            scope.animationsEnabled = true;
            scope.getImage = function () {
                if (!scope.userProfile.userImage) {
                    return '../../images/default_avatar.png';
                }
                else {
                    return 'data:image/jpeg;base64,' + scope.userProfile.userImage;
                }
            };

            scope.open = function (size, parentSelector) {
                var modalInstance = $uibModal.open({
                    animation: scope.animationsEnabled,
                    templateUrl: 'partials/userSearch/modalWindow/modalWindow.template.html',
                    controller: function ($uibModalInstance) {
                        var vm = this;
                        vm.currentImage = scope.originImage;
                        vm.fullName = scope.userProfile.firstName + " " + scope.userProfile.lastName;

                    },
                    controllerAs: 'vm',
                    size: 'lg'
                });
            };
            scope.openModalWindowFullImageSize = function () {
                userSearchModel.getUserInfo(scope.userProfile.originalUserImageId).then(
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
            };
        }
    };
});