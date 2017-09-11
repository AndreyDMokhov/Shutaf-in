"use strict";
app.controller('resetPasswordConfirmation', function ($filter, resetPasswordModel, $state, notify, $stateParams) {
    var vm = this;
    var urlLink = $stateParams.link;

    vm.isLinkValid = false;


    function validateUrlLink() {
        if (urlLink === undefined || urlLink === null || urlLink === "") {
            $state.go("error", {'code': '404'});
        }

        resetPasswordModel.resetPasswordValidate(urlLink).then(
            function (success) {
                vm.isLinkValid = true;
            },
            function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                if (error.data.error.errorTypeCode === 'RNF') {
                    $state.go("error", {'code': '404'});
                }
            }
        );
    }

    function sendNewPassword() {

        resetPasswordModel.resetPasswordChange({link: urlLink, newPassword:vm.password.newPassword}).then(
            function (success) {
                notify.set($filter('translate')("ResetPassword.passwordChanged"), {type: 'success'});
                $state.go("home");
            },
            function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                if (error.data.error.errorCodeType === 'RNF') {
                    $state.go("error", {'code': '404'});
                }
            }
        )
    }


    validateUrlLink();
    vm.sendNewPassword = sendNewPassword;

});

