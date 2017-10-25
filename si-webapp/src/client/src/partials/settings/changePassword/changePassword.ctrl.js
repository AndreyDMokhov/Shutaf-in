"use strict";
app.controller('changePasswordController', function ($rootScope, changePasswordModel, notify, $filter, $state, $window) {
    $window.document.title = "Shutaf-In | " +$filter('translate')('Settings.security.password.title');
    var vm = this;

    vm.isOpened = true;

    vm.dataLoading = false;

    vm.securitySettings = {};


    function changePassword() {
        vm.dataLoading=true;

        changePasswordModel.changePassword(vm.securitySettings).then(
            function (success) {
                vm.dataLoading = false;
                notify.set($filter('translate')("Settings.security.msg.success"), {type: 'success'});
                $state.go('settings');
            }, function (error) {
                vm.dataLoading = false;

                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                if (error.data.error.errorTypeCode === 'AUT') {
                    $state.go('logout');
                }
            });
    }


    vm.changePassword = changePassword;

});
