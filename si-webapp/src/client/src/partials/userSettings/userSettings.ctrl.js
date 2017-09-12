app.controller("userSettingsController", function ($localStorage, $sessionStorage, userSettingsModel, notify, $filter, $state, userInitService) {

    var vm = this;
    vm.dataLoading = false;
    vm.isCollapsed = true;

    vm.userProfile = $sessionStorage.userProfile;

    vm.country = $localStorage.countries;
    vm.cities = $localStorage.cities;
    vm.gender = $localStorage.genders;

    // if number of countries more than one, delete next line
    vm.userProfile.countryId = 1;

    function submitChanges() {
        vm.dataLoading = true;
        userSettingsModel.saveDataPostRegistration(vm.userProfile).then(
            function (success) {
                notify.set($filter('translate')('UserSettings.message.save.success'), {type: 'success'});
                userInitService.init();
                vm.dataLoading = false;
            }, function (error) {
                vm.dataLoading = false;
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                if (error.data.error.errorTypeCode === 'AUT') {
                    $state.go('logout');
                }
            });
    }

    vm.submitChanges = submitChanges;

});