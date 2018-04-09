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
                          userSearchService) {

        var vm = this;
        vm.genders = $sessionStorage.genders;
        vm.cities = $sessionStorage.cities;
        vm.fullName = $stateParams.name;
        vm.userSearchList = [];
        vm.totalUsers = 0;
        var page = 0;
        vm.isDisable = false;
        vm.isLoading = false;

        function activate() {
            userSearchService.registerFilterObserver(saveFilters);
            userSearch();
        }

        function isUnique(value, index, self) {
            return self.lastIndexOf(value['userId']) === index;
        }

        function userSearch() {
            userSearchModel.userSearch(vm.fullName, page).then(
                function (success) {
                    if(success.data.data.matchedUsersPerPage.length == 0){
                        vm.isDisable = true;
                        vm.isLoading = false;
                    }
                    else{
                        vm.totalUsers = success.data.data.totalUsers;
                        Array.prototype.push.apply(vm.userSearchList, success.data.data.matchedUsersPerPage);
                        vm.isDisable = false;
                        vm.isLoading = false;
                        vm.userSearchList.filter(isUnique);
                    }

                }, function (error) {
                    vm.isDisable = false;
                    vm.isLoading = false;
                    if (error === undefined || error === null) {
                        notify.set($filter('translate')('Error.SYS'), {type: 'error'});
                    }else {
                        notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                        if (error.data.error.errorTypeCode === 'AUT') {
                            $state.go('logout');
                        }
                    }
                });
        }

        function saveFilters(filters) {
            userSearchModel.saveFilters(filters, vm.fullName).then(
                function (success) {
                    $sessionStorage.filters.filterGenderId = filters.filterGenderId;
                    $sessionStorage.filters.filterCitiesIds = filters.filterCitiesIds;
                    $sessionStorage.filters.filterAgeRange = filters.filterAgeRange;
                    vm.userSearchList = success.data.data;
                    $state.go($state.current, {}, {reload: true});
                }, function (error) {
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                });
        }

        function openUserProfile(userId) {
            $state.go('userProfile', userId);
        }

        function fetchNewPage () {
            page++;
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