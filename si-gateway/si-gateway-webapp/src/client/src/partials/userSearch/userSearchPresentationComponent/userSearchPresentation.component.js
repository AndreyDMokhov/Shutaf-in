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
        vm.userSearchList = [];
        var page = 0;
        vm.isDisable = false;
        vm.isLoading = false;

        function activate() {
            userSearchService.registerFilterObserver(saveFilters);
            userSearch();
        }

        function userSearch() {
            userSearchModel.userSearch(vm.fullName, page).then(
                function (success) {
                    if(success.data.data.length == 0){
                        vm.isDisable = true;
                        vm.isLoading = false;
                    }
                    else{
                        Array.prototype.push.apply(vm.userSearchList, success.data.data);
                        page++;
                        vm.isDisable = false;
                        vm.isLoading = false;
                    }

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

        function fetchNewPage () {
            vm.isDisable = true;
            vm.isLoading = true;
            userSearch();
        }

        activate();

        vm.userSearch = userSearch;
        vm.openUserProfile = openUserProfile;
        vm.saveFilters = saveFilters;
        vm.fetchNewPage = fetchNewPage;
    }
});