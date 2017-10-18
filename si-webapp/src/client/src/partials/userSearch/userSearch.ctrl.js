"use strict";
app.controller("userSearchController", function ($state, $sessionStorage, notify, sessionService, userSearchModel, $stateParams, $filter,$scope,$rootScope, $timeout, $modal) {
    var vm = this;

    vm.userSearchList = {};
    vm.fullName = $stateParams.name;
    vm.cities = $sessionStorage.cities;
    vm.genders = $sessionStorage.genders;
    vm.countries = $sessionStorage.countries;
    // vm.selectedGender = vm.genders[0].id;
    vm.selectedGender = '';
    vm.selectedCountry = '';
    vm.selectedCity = '';
    console.log(vm.cities);
    console.log(vm.selectedCountry);

    function activate() {
        userSearch();
    }

    function userSearch() {

        userSearchModel.userSearch(vm.fullName).then(
            function (success) {

                vm.userSearchList = success.data.data;

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

    activate();

    vm.minRangeSlider = {
        minValue: 25,
        maxValue: 32,
        options: {
            floor: 18,
            ceil: 60,
            step: 1
        }
    };
    function doIt() {
        alert("minAge = " + vm.minRangeSlider.minValue + " maxAge = " + vm.minRangeSlider.maxValue+
        " gender = " + vm.selectedGender +" country = " + vm.selectedCountry +" city = " + vm.selectedCity);
        console.log(vm.selectedGender);
        console.log(vm.selectedCountry);
        console.log(vm.selectedCity);
    }


    vm.userSearch = userSearch;
    vm.getImage = getImage;
    vm.doIt = doIt;
});
