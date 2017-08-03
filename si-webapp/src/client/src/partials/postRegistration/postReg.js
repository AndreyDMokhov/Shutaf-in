app
.controller("preSettingsController", function ($localStorage) {

    var vm = this;
    vm.dataLoading = false;
    vm.preSettings={};
    vm.country = $localStorage.countries;
    vm.cities = $localStorage.cities;
    vm.gender = $localStorage.genders;
    // console.log(vm.country)

    function submitChanges() {
        console.log(vm.preSettings)
        // vm.dataLoading = true;
        // settingsModel.submitChanges(vm.accountSettings).then(
        //     function (success) {
        //         vm.dataLoading = false;
        //         userInitService.init();
        //
        //         notify.set($filter('translate')('Settings.personal.message.save.success'), {type: 'success'});
        //
        //     }, function (error) {
        //         vm.dataLoading = false;
        //         notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
        //
        //         if (error.data.error.errorTypeCode === 'AUT') {
        //             $state.go('logout');
        //         }
        //     });
    }

    vm.submitChanges = submitChanges;



});