app.controller('settingsController', function (settingsModel, userInitService, languageService, constantService, $filter, notify, $sessionStorage) {
    var vm = this;
    vm.dataLoading = false;

    vm.accountSettings = $sessionStorage.userProfile;


    function submitChanges() {
        vm.dataLoading = true;
        settingsModel.submitChanges(vm.accountSettings).then(
            function (success) {
                vm.dataLoading = false;
                userInitService.init();

                notify.set($filter('translate')('Settings.personal.message.save.success'), {type: 'success'});

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