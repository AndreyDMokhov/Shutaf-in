"use strict";
app.component('myUserProfileComponent', {
    templateUrl: 'partials/userProfile/myUserProfile.component.html',
    bindings: {
        dialogUserId: '='
    },
    controllerAs: 'vm',
    controller: function ($localStorage,
                          $state,
                          $stateParams,
                          $filter,
                          myUserProfileModel,
                          $sessionStorage,
                          notify,
                          browserTitle) {

        var vm = this;
        vm.userProfile = $sessionStorage.userProfile;
        browserTitle.setExplicitTitle(vm.userProfile.firstName + " " + vm.userProfile.lastName);
        vm.hideEditButton = false;
        vm.enableDisableProfileImageTooltip = true;
        vm.cities = $sessionStorage.cities;
        vm.genders = $sessionStorage.genders;

        function loadSearchResultsUserProfile() {
            var profileId = null;
            var isModalRequest = vm.dialogUserId != null;

            if (!isModalRequest) {
                profileId = $stateParams.id;

                var hasProfileIdInParam = profileId !== undefined && profileId !== null && profileId !== '';

                if (!hasProfileIdInParam) {
                    $state.go('error', {code: '404'});
                }
            } else {

                profileId = vm.dialogUserId.id;
            }

            vm.isThisMyProfile = vm.userProfile.userId === profileId;

            vm.enableDisableProfileImageTooltip = true;


            myUserProfileModel.getSelectedUserProfile(profileId).then(
                function (success) {
                    if (success.data.data === null) {
                        $state.go('error', {code: 404});
                    }
                    vm.userProfile = success.data.data;
                    if (!vm.userProfile.originalUserImage) {
                        vm.image = '../../images/default_avatar.png';
                    }
                    else {
                        vm.image = 'data:image/jpeg;base64,' + vm.userProfile.originalUserImage;
                    }
                },
                function (error) {
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                }
            );

        }

        loadSearchResultsUserProfile();
    }


});