app.controller('securitySettingsPasswordController', function (securitySettingsPasswordModel, notify, $state, $filter/*, $stateParams*/) {

    var vm = this;
    //var urlLink = $stateParams.urlLink;

    vm.dataLoading = false;

    vm.securitySettings = {};


    function updatePassword() {
        vm.dataLoading=true;

        securitySettingsPasswordModel.updatePassword(vm.securitySettings).then(
            function (success) {
                vm.dataLoading = false;
                notify.set($filter('translate')("SecuritySettings.msg.success"), {type: 'success'});
            }, function (error) {
                vm.dataLoading = false;
                notify.set($filter('translate')("SecuritySettings.msg.fail"), {type: 'error'});
            });
    }


    vm.updatePassword = updatePassword;

});
