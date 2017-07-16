app.controller('resetPasswordController', function (resetPassword, notify, $filter) {
    var vm = this;
    vm.resetPasswordEmail = {};
    vm.emailConfirm = false;
    vm.loading = false;

    function requestIfEmailExist() {
        vm.loading = true;
        resetPassword.requestResetPassword(vm.resetPasswordEmail).then(
            function (sucess) {
                vm.emailConfirm = true;
                notify.set($filter('translate')("ResetPassword.emaiSent"));
            },function (error) {
            }).finally(function() {
            vm.loading = false;
        });
    };

vm.requestIfEmailExist=requestIfEmailExist;

});
