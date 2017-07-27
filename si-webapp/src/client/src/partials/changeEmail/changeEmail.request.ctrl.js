app.controller('changeEmailRequestController', function ($state, $rootScope, securitySettingsChangeEmailModel, notify, $filter) {

    var vm = this;
    vm.dataLoading = false;
    vm.securitySettings = {};

    function emailChangeRequest() {
        vm.dataLoading = true;

        securitySettingsChangeEmailModel.emailChangeRequest(vm.securitySettings).then(
            function (success) {
                vm.dataLoading = false;
                notify.set($filter('translate')("SecuritySettings.msg.successRequest"), {type: 'success'});
                $state.go("home");
            }, function (error) {
                vm.dataLoading = false;
                if (error.data.error.errorTypeCode === 'EDE') {

                    notify.set($filter('translate')("SecuritySettings.msg.error.emailDuplication"), {type: 'error'});
                } else if (error.data.error.errorTypeCode === 'AUT') {

                    notify.set($filter('translate')("SecuritySettings.msg.error.authentication"), {type: 'error'});
                } else {

                    notify.set($filter('translate')("SecuritySettings.msg.systemError"), {type: 'error'});
                }
            });
    }

    vm.emailChangeRequest = emailChangeRequest;
});
