app.controller("userSettingsController", function ($localStorage, $sessionStorage, userSettingsModel, notify, $filter, $state, userInitService) {

    var vm = this;
    vm.dataLoading = false;
    vm.isCollapsed = true;

    vm.userProfile = $sessionStorage.userProfile;

    vm.country = $sessionStorage.countries;
    vm.cities = $sessionStorage.cities;
    vm.gender = $sessionStorage.genders;
    vm.val = dateCorrect() ;


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

    function dateCorrect() {
        return $filter('date')(vm.userProfile.dateOfBirth,'yyyy-MM-dd');;
    }

    vm.submitChanges = submitChanges;

});