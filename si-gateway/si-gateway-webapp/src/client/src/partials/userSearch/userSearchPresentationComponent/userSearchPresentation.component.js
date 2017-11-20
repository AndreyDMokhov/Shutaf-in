'use strict';
app.component('userSearchPresentationComponent', {
    templateUrl: 'partials/userSearch/userSearchPresentationComponent/userSearchPresentation.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function ($state,
                          $sessionStorage,
                          notify,
                          sessionService,
                          userSearchModel,
                          $stateParams,
                          $filter,
                          ngDialog,
                          $scope,
                          userSearchService) {

        var vm = this;

        vm.fullName = $stateParams.name;

        function activate() {
            userSearch();
        }

        function userSearch() {
            userSearchModel.userSearch(vm.fullName).then(
                function (success) {

                    vm.userSearchList = success.data.data;
                    // $sessionStorage.userSearchList = vm.userSearchList;
                }, function (error) {
                    if (error === undefined || error === null) {
                        notify.set($filter('translate')('Error.SYS'), {type: 'error'});
                    }
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                });
        }

        function getImage(userProfile) {
            if (!userProfile.userImage) {
                return '../../images/default_avatar.png';
            }
            else {
                return 'data:image/jpeg;base64,' + userProfile.userImage;
            }
        }

        function showUserProfilePopup(userId) {
            vm.userId = userId;
            ngDialog.open({
                scope: $scope,
                template: '<user-profile-component dialog-user-id="vm.userId"></user-profile-component>',
                plain: true,
                className: 'ngdialog-theme-default custom-user-profile-popup',
                url: '/profile'
            });
        }

        userSearchService.onSearchListRefreshed(function (answer) {
            vm.userSearchList = answer;
        });

        activate();

        vm.userSearch = userSearch;
        vm.getImage = getImage;
        vm.showUserProfilePopup = showUserProfilePopup;
    }

});