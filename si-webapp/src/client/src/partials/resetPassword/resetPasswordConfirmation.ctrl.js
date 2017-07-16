app.controller('resetPasswordConfirmation', function ($stateParams, $filter, resetPassword, $state, notify) {
    var linkUUID = $stateParams.link;
    var vm = this;
    vm.password = {link: linkUUID};

    function sendLinkResetPasswordToEmail() {
        resetPassword.resetPasswordValidate(linkUUID).then(
            function (success) {
                $state.go("inputNewPassword");
            },
            function (error) {
                $state.go("error404");
            }
        )
    };

    function sendNewPassword() {
        resetPassword.resetPasswordChange(vm.password).then(
            function (success) {
                notify.set($filter('translate')("ResetPassword.passChanged"), {type: 'success'});
                $state.go("home");
            },
            function (error) {
                notify.set($filter('translate')("ResetPassword.unexpectedError"), {type: 'error'});
            }
        )
    };
    sendLinkResetPasswordToEmail();
    vm.sendLinkResetPasswordToEmail = sendLinkResetPasswordToEmail;
    vm.sendNewPassword = sendNewPassword;


})

