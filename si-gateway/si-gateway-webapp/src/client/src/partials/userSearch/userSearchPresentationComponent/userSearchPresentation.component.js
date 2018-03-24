'use strict';
app.component('userSearchPresentationComponent', {
    templateUrl: 'partials/userSearch/userSearchPresentationComponent/userSearchPresentation.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function ($state,
                          $sessionStorage,
                          notify,
                          sessionService,
                          userSearchModel,
                          $stateParams,
                          $filter,
                          ngDialog,
                          $scope,
                          userSearchService) {

        var vm = this;
        vm.genders = $sessionStorage.genders;
        vm.cities = $sessionStorage.cities;
        vm.fullName = $stateParams.name;

        function activate() {
            userSearchService.registerFilterObserver(saveFilters);
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

        function saveFilters(filters) {
            userSearchModel.saveFilters(filters, vm.fullName).then(
                function (success) {
                    $sessionStorage.filters.filterGenderId = filters.filterGenderId;
                    $sessionStorage.filters.filterCitiesIds = filters.filterCitiesIds;
                    $sessionStorage.filters.filterAgeRange = filters.filterAgeRange;

                    vm.userSearchList = success.data.data;
                }, function (error) {
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                });
        }

        function openUserProfile(userId) {
            $state.go('userProfile', userId);
        }

        activate();

        vm.userSearch = userSearch;
        vm.openUserProfile = openUserProfile;
        vm.saveFilters = saveFilters;
    }

});