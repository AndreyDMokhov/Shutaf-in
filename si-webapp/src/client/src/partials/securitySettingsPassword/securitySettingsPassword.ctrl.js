app.controller('securitySettingsPasswordController', function ($rootScope, securitySettingsPasswordModel, notify, $filter /*, $stateParams*/) {

    var vm = this;
    //var urlLink = $stateParams.urlLink;

    vm.dataLoading = false;

    vm.securitySettings = {};


    function changePassword() {
        vm.dataLoading=true;

        securitySettingsPasswordModel.changePassword(vm.securitySettings).then(
            function (success) {
                vm.dataLoading = false;
                notify.set($filter('translate')("SecuritySettings.msg.success"), {type: 'success'});
            }, function (error) {
                vm.dataLoading = false;
                notify.set($filter('translate')("SecuritySettings.msg.fail"), {type: 'error'});
            });
    }


    vm.changePassword = changePassword;

});
