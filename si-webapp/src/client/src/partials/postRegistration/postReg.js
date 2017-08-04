app
.controller("preSettingsController", function ($localStorage, $scope) {

    var vm = this;
    vm.dataLoading = false;
    vm.isCollapsed = true;

    vm.preSettings={};
    vm.country = $localStorage.countries;
    vm.cities = $localStorage.cities;
    vm.gender = $localStorage.genders;


    vm.preSettings.country=vm.country[0].description

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