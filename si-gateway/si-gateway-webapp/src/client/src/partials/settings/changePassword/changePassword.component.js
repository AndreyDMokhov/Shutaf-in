"use strict";
app.component('changePasswordComponent', {
    templateUrl: 'partials/settings/changePassword/changePassword.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function (
        $rootScope,
        changePasswordModel,
        $filter,
        $state,
        browserTitleService,
        uiNotification) {

    browserTitleService.setBrowserTitleByFilterName('Settings.security.password.title');
        var vm = this;

        vm.isOpened = true;

        vm.dataLoading = false;

        vm.securitySettings = {};


        function changePassword() {
            vm.dataLoading = true;

            changePasswordModel.changePassword(vm.securitySettings).then(
                function (success) {
                    vm.dataLoading = false;

                    var message = $filter('translate')("Settings.security.msg.success");
                    uiNotification.show(message, 'success');

                    $state.go('settings');
                }, function (error) {
                    vm.dataLoading = false;
                });
        }


        vm.changePassword = changePassword;
    }
});
