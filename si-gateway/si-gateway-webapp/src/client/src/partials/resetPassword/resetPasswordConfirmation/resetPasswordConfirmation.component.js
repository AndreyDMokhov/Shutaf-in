app.component('resetPasswordConfirmationComponent', {
    templateUrl: 'partials/resetPassword/resetPasswordConfirmation/resetPasswordConfirmation.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function (
        $filter,
        resetPasswordModel,
        $state,
        uiNotification,
        $stateParams,
        browserTitleService) {

    browserTitleService.setBrowserTitleByFilterName('ResetPassword.title');

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
                }
            );
        }

        function sendNewPassword() {

            resetPasswordModel.resetPasswordChange({link: urlLink, newPassword: vm.password.newPassword}).then(
                function (success) {
                    uiNotification.show($filter('translate')("ResetPassword.passwordChanged"), 'success');
                    $state.go("home");
                },
                function (error) {
                }
            );
        }


        validateUrlLink();
        vm.sendNewPassword = sendNewPassword;

    }
});

