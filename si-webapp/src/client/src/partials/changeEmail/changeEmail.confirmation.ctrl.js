app.controller('changeEmailConfirmationController', function ($state, $rootScope, securitySettingsChangeEmailModel, notify, $filter, $stateParams) {

    var vm = this;

    vm.dataLoading = false;
    vm.securitySettings = {};

    function changeEmail() {
        vm.dataLoading = true;
        var urlLink = $stateParams.link;
        if (urlLink === undefined || urlLink === null || urlLink === "") {
            $state.go("error", {'code': '404'});
        }
        securitySettingsChangeEmailModel.emailChangeConfirmation(urlLink).then(
            function (success) {
                vm.dataLoading = false;
                if (success.emailChanged === false) {

                    notify.set($filter('translate')("SecuritySettings.firstEmailConfirmed.success"), {type: 'success'});
                    $state.go('home');
                }

                if (success.emailChanged === true) {

                    notify.set($filter('translate')("SecuritySettings.secondEmailConfirmed.success"), {type: 'success'});
                    $state.go("logout");
                }
            }, function (error) {
                vm.dataLoading = false;

                if (error.data.error.errorTypeCode === 'RNF') {
                    $state.go("error", {'code': status});
                } else {
                    notify.set($filter('translate')("SecuritySettings.common.failure"), {type: 'error'})
                }
            })
    };

    changeEmail();

});
