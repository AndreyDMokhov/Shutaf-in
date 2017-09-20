"use strict";
app.controller('resetPasswordRequestController', function (resetPasswordModel, notify, $filter, $state) {
    var vm = this;
    vm.resetPasswordEmail = {};
    vm.emailConfirm = false;
    vm.loading = false;

    function resetPasswordRequest() {
        vm.loading = true;
        resetPasswordModel.requestResetPassword(vm.resetPasswordEmail).then(
            function (success) {
                vm.emailConfirm = true;
                notify.set($filter('translate')("ResetPassword.emailSent"));
                $state.go('home');
            }, function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
            })
            .finally(function () {
                vm.loading = false;
            });
    }

    vm.resetPasswordRequest = resetPasswordRequest;

});
