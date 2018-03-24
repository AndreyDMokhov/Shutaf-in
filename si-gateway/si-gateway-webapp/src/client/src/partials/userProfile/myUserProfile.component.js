"use strict";
app.component('myUserProfileComponent', {
    templateUrl: 'partials/userProfile/myUserProfile.component.html',
    controllerAs: 'vm',
    controller: function ($sessionStorage,
                          browserTitle) {

        var vm = this;
        vm.userProfile = $sessionStorage.userProfile;
        browserTitle.setExplicitTitle(vm.userProfile.firstName + " " + vm.userProfile.lastName);
    }
});