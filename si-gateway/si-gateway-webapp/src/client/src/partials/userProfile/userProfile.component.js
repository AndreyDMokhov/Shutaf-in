"use strict";
app.component('userProfileComponent', {
    templateUrl: 'partials/userProfile/userProfile.component.html',
    controllerAs: 'vm',
    controller: function ($state,
                          $sessionStorage,
                          $stateParams,
                          userProfileModel,
                          browserTitle) {

        var vm = this;
        vm.userInfo = {};

        if ($stateParams.userId) {
            if ($stateParams.userId === $sessionStorage.userProfile.userId) {
                $state.go('myUserProfile');
            } else {
                getUserProfile().then(
                    function () {
                        browserTitle.setExplicitTitle(vm.userInfo.firstName + ' ' + vm.userInfo.lastName);
                    }
                );
            }

        } else {
            $state.go('home');
        }

        function getUserProfile() {
            return userProfileModel.getSelectedUserProfile($stateParams.userId).then(
                function (success) {
                    if (success.data.data === null) {
                        $state.go('error', {code: 404});
                    }
                    vm.userInfo = success.data.data;
                },
                function (error) {
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                }
            );
        }


    }
});