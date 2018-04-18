app.component('resetPasswordRequestComponent', {
    templateUrl: 'partials/resetPassword/resetPasswordRequest/resetPasswordRequest.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function (
        resetPasswordModel,
        uiNotification,
        $filter,
        $state,
        browserTitleService) {

    browserTitleService.setBrowserTitleByFilterName('ResetPassword.title');
        var vm = this;
        vm.resetPasswordEmail = {};
        vm.emailConfirm = false;
        vm.loading = false;

        function resetPasswordRequest() {
            vm.loading = true;
            resetPasswordModel.requestResetPassword(vm.resetPasswordEmail).then(
                function (success) {
                    vm.emailConfirm = true;
                    uiNotification.show($filter('translate')("ResetPassword.emailSent"));
                    $state.go('home');
                }, function (error) {

                })
                .finally(function () {
                    vm.loading = false;
                });
        }

        vm.resetPasswordRequest = resetPasswordRequest;

    }
});
