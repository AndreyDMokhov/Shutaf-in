"use strict";
app.controller("userSearchController", function ($state,
                                                 $sessionStorage,
                                                 notify,
                                                 sessionService,
                                                 userSearchModel,
                                                 $stateParams,
                                                 $filter,
                                                 browserTitle) {

    browserTitle.setBrowserTitleByFilterName('Search.title');
    var vm = this;

    vm.userSearchList = {};
    vm.fullName = $stateParams.name;
    vm.cities = $sessionStorage.cities;
    vm.genders = $sessionStorage.genders;
    vm.countries = $sessionStorage.countries;


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

    vm.userSearch = userSearch;
    vm.getImage = getImage;
});
