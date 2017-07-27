app.controller('userSettingsController', function (userSettingsModel, userInitService, languageService, constantService, $filter, notify) {
    var vm = this;
    vm.dataLoading = false;

    vm.accountSettings = userInitService.getUserProfile();


    function submitChanges() {
        vm.dataLoading = true;
        userSettingsModel.submitChanges(vm.accountSettings).then(
            function (success) {
                vm.dataLoading = false;
                userInitService.init();

                notify.set($filter('translate')('UserSettings.message.save.success'), {type: 'success'});

            }, function (error) {
                vm.dataLoading = false;

                notify.set($filter('translate')('UserSettings.message.save.fail'), {type: 'error'});
                console.log(error);
            });
    }



    vm.submitChanges = submitChanges;

});