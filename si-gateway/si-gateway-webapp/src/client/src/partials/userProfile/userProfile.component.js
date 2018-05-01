"use strict";
app.component('userProfileComponent', {
    templateUrl: 'partials/userProfile/userProfile.component.html',
    controllerAs: 'vm',
    controller: function ($state,
                          $sessionStorage,
                          $stateParams,
                          userProfileModel,
                          browserTitleService) {

        var vm = this;
        vm.userInfo = {};

        if ($stateParams.userId) {
            if ($sessionStorage.userProfile && $stateParams.userId === $sessionStorage.userProfile.userId) {

                $state.go('myUserProfile');
            } else {
                getUserProfile().then(
                    function () {
                        browserTitleService.setExplicitTitle(vm.userInfo.firstName + ' ' + vm.userInfo.lastName);
                    }
                );
            }

        } else {
            $state.go('home');
        }

        function getUserProfile() {
            return userProfileModel.getSelectedUserProfile($stateParams.userId).then(
                function (success) {
                    if (!success.data.data) {
                        $state.go('error', {code: 404});
                    }
                    vm.userInfo = success.data.data;
                },
                function (error) {
                }
            );
        }


    }
});