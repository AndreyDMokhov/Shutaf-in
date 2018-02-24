"use strict";
app.component('myUserProfileInfo', {
    templateUrl: 'partials/userProfile/userProfileInfo/myUserProfileInfo.component.html',
    bindings: {
        userProfile: '='
    },
    controllerAs: 'vm',
    controller: function ($sessionStorage) {
        var vm = this;
        vm.cities = $sessionStorage.cities;
        vm.genders = $sessionStorage.genders;
        vm.countries = $sessionStorage.countries;
    }
});