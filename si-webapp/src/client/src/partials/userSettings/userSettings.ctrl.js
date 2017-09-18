app.controller("userSettingsController", function ($localStorage,
                                                   $sessionStorage,
                                                   userSettingsModel,
                                                   notify,
                                                   $filter,
                                                   $state,
                                                   initializationService) {

    var vm = this;
    vm.dataLoading = false;
    vm.isCollapsed = true;

    vm.userProfile = $sessionStorage.userProfile;

    vm.country = $sessionStorage.countries;
    vm.cities = $sessionStorage.cities;
    vm.gender = $sessionStorage.genders;


    function submitChanges() {
        vm.dataLoading = true;
        userSettingsModel.saveDataPostRegistration(vm.userProfile).then(
            function () {
                vm.dataLoading = false;
                notify.set($filter('translate')('UserSettings.message.save.success'), {type: 'success'});
                initializationService.initializeApplication();
                $state.go('userProfile', {id: $sessionStorage.userProfile.userId});
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