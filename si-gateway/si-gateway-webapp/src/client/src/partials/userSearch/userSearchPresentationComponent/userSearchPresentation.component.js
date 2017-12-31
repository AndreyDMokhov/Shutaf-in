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
        vm.genders = $sessionStorage.genders;
        vm.cities = $sessionStorage.cities;

        vm.fullName = $stateParams.name;

        function activate() {
            userSearch();
        }

        function userSearch() {
            userSearchModel.userSearch(vm.fullName).then(
                function (success) {
                    vm.userSearchList = success.data.data;
                    saveUserImagesToStorage();
                }, function (error) {
                    if (error === undefined || error === null) {
                        notify.set($filter('translate')('Error.SYS'), {type: 'error'});
                    }
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                });
        }

        function saveUserImagesToStorage() {
            angular.forEach(vm.userSearchList, function (item) {
                if(item.userImage && !$sessionStorage[item.userId]){
                    $sessionStorage[item.userId] = item.userImage;
                }
            });
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
        vm.showUserProfilePopup = showUserProfilePopup;
    }

});