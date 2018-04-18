"use strict";
app.component('myUserProfileComponent', {
    templateUrl: 'partials/userProfile/myUserProfile.component.html',
    controllerAs: 'vm',
    controller: function ($sessionStorage,
                          browserTitleService,
                          sessionStorageObserver) {

        var vm = this;
        vm.userProfile = $sessionStorage.userProfile;

        browserTitleService.setExplicitTitle(vm.userProfile.firstName + " " + vm.userProfile.lastName);

        function userProfileUpdated() {
            vm.userProfile = $sessionStorage.userProfile;
        }

        sessionStorageObserver.registerServiceObserver(userProfileUpdated);
    }
});