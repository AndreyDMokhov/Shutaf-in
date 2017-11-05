"use strict";
app.controller("userSearchController", function (
    $state,
    $sessionStorage,
    notify,
    sessionService,
    userSearchModel,
    $stateParams,
    $filter,
    $scope,
    $rootScope,
    $timeout,
    $modal,
    browserTitle) {

    var vm = this;

    browserTitle.setBrowserTitleByFilterName('Search.title');
    vm.age = false;
    vm.gender = false;
    vm.city = false;
    vm.searchData = {};
    vm.userSearchList = {};
    vm.fullName = $stateParams.name;
    vm.cities = $sessionStorage.cities;
    vm.genders = $sessionStorage.genders;
    vm.countries = $sessionStorage.countries;

    vm.searchData.filterGenderId = $sessionStorage.filters.filterGenderId;
    vm.searchData.filterCitiesIds = $sessionStorage.filters.filterCitiesIds;
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


    function saveFilters() {
        setAgeRangeData();
        if (!vm.city) {
            vm.searchData.filterCitiesIds = null;
        }
        if (!vm.gender) {
            vm.searchData.filterGenderId = null;
        }
        userSearchModel.saveFiltersInDB(vm.searchData).then(
            function (success) {
                $sessionStorage.filters.filterGenderId = vm.searchData.filterGenderId;
                $sessionStorage.filters.filterCitiesIds = vm.searchData.filterCitiesIds;
                $sessionStorage.filters.filterAgeRange = vm.searchData.filterAgeRange;

                vm.userSearchList = success.data.data;
            }, function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
            });
    }

    function fillAgeRange() {
        if (vm.searchData.filterAgeRange !== null) {
            vm.minRangeSlider.minValue = vm.searchData.filterAgeRange.fromAge;
            vm.minRangeSlider.maxValue = vm.searchData.filterAgeRange.toAge;
        }
        else {
            vm.minRangeSlider.minValue = 25;
            vm.minRangeSlider.maxValue = 50;
        }
    }

    function setAgeRangeData() {
        if (!vm.age) {
            vm.searchData.filterAgeRange = null;
        }
        else {
            vm.searchData.filterAgeRange = {
                fromAge: vm.minRangeSlider.minValue,
                toAge: vm.minRangeSlider.maxValue
            };
        }
    }

    vm.userSearch = userSearch;
    vm.getImage = getImage;
    vm.saveFilters = saveFilters;
});
