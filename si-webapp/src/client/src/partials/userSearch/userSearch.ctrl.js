"use strict";
app.controller("userSearchController", function ($state, $sessionStorage, notify, sessionService, userSearchModel, $stateParams, $filter,$scope,$rootScope, $timeout, $modal) {
    var vm = this;
    vm.searchData={};
    vm.userSearchList = {};
    vm.fullName = $stateParams.name;
    vm.cities = $sessionStorage.cities;
    vm.genders = $sessionStorage.genders;
    vm.searchData.filterGenderId = $sessionStorage.filters.filterGenderId;
    vm.searchData.filterCitiesIds = $sessionStorage.filters.filterCitiesIds ;
    vm.searchData.filterAgeRange = $sessionStorage.filters.filterAgeRange;
    vm.minRangeSlider = {
        options: {
            floor: 18,
            ceil: 120,
            step: 1
        }
    };
    fillAgeRange();


    function activate() {
        userSearch();
    }

    function userSearch() {
        console.log(vm.fullName);

        userSearchModel.userSearch(vm.fullName).then(
            function (success) {

                vm.userSearchList = success.data.data;
                console.log(vm.userSearchList);

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


    function doIt() {
        vm.searchData.filterAgeRange = {
            fromAge: vm.minRangeSlider.minValue,
            toAge: vm.minRangeSlider.maxValue
        };
        userSearchModel.saveFilters(vm.searchData).then(
            function (success) {
                console.log(success);
                $sessionStorage.filters.filterGenderId = vm.searchData.filterGenderId ;
              $sessionStorage.filters.filterCitiesIds = vm.searchData.filterCitiesIds ;
              $sessionStorage.filters.filterAgeRange = vm.searchData.filterAgeRange;
            }, function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
            });
    }
function fillAgeRange() {
    if(vm.searchData.filterAgeRange===null){
        vm.minRangeSlider.minValue=25;
        vm.minRangeSlider.maxValue=50;
    }
    else{
    vm.minRangeSlider.minValue=vm.searchData.filterAgeRange.fromAge;
    vm.minRangeSlider.maxValue=vm.searchData.filterAgeRange.toAge;
    }
}

    vm.userSearch = userSearch;
    vm.getImage = getImage;
    vm.doIt = doIt;
});
