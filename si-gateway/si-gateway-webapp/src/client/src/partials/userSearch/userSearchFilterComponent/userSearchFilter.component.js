'use strict';
app.component('userSearchFilterComponent', {
    templateUrl: 'partials/userSearch/userSearchFilterComponent/userSearchFilter.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function ($sessionStorage,
                          userSearchModel,
                          $stateParams,
                          $filter,
                          userSearchObserver) {

        var vm = this;

        vm.age = false;
        vm.gender = false;
        vm.city = false;
        vm.searchData = {};
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
        fillGenderAndCity();

        function fillAgeRange() {
            if (vm.searchData.filterAgeRange !== null) {
                vm.age = true;
                vm.minRangeSlider.minValue = vm.searchData.filterAgeRange.fromAge;
                vm.minRangeSlider.maxValue = vm.searchData.filterAgeRange.toAge;
            }
            else {
                vm.minRangeSlider.minValue = 25;
                vm.minRangeSlider.maxValue = 50;
            }
        }

        function fillGenderAndCity() {
            if (vm.searchData.filterGenderId) {
                vm.gender = true;
            }
            if (vm.searchData.filterCitiesIds) {
                vm.city = true;
            }
        }

        function saveFilters() {
            setAgeRangeData();
            if (!vm.city) {
                vm.searchData.filterCitiesIds = null;
            }
            if (!vm.gender) {
                vm.searchData.filterGenderId = null;
            }
            userSearchObserver.notifyFilterObserver(vm.searchData);
        }

        function setAgeRangeData() {
            if (!vm.age) {
                vm.searchData.filterAgeRange = null;
            } else {
                vm.searchData.filterAgeRange = {
                    fromAge: vm.minRangeSlider.minValue,
                    toAge: vm.minRangeSlider.maxValue
                };
            }
        }

        vm.saveFilters = saveFilters;
    }

});