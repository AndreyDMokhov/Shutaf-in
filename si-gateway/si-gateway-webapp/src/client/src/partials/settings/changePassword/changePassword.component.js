"use strict";
app.component('changePasswordComponent', {
    templateUrl: 'partials/settings/changePassword/changePassword.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function (
        $rootScope,
        changePasswordModel,
        notify,
        $filter,
        $state,
        browserTitle) {

    browserTitle.setBrowserTitleByFilterName('Settings.security.password.title');
        var vm = this;

        vm.isOpened = true;

        vm.dataLoading = false;

        vm.securitySettings = {};


        function changePassword() {
            vm.dataLoading = true;

            changePasswordModel.changePassword(vm.securitySettings).then(
                function (success) {
                    vm.dataLoading = false;
                    notify.set($filter('translate')("Settings.security.msg.success"), {type: 'success'});
                    $state.go('settings');
                }, function (error) {
                    vm.dataLoading = false;
                });
        }


        vm.changePassword = changePassword;
    }
});
