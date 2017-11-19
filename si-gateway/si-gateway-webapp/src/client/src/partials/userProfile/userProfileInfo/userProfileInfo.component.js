"use strict";
app.component('userProfileInfo', {
    templateUrl: 'partials/userProfile/userProfileInfo/userProfileInfo.component.html',
    bindings: {
        userProfile: '=',
        prosto: '=',
        dialogUserId123: '='
    },
    controllerAs: 'vm',
    controller: function ($sessionStorage) {
        var vm = this;
        vm.cities = $sessionStorage.cities;
        vm.genders = $sessionStorage.genders;
    }
});