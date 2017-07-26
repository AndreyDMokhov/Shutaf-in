app.controller('securitySettingsChangeEmailConfirmController', function ($state, $rootScope, securitySettingsChangeEmailModel, notify, $filter, $stateParams, emailChangeConfirmationService) {

    var vm = this;

    vm.dataLoading = false;
    vm.securitySettings = {};

    function emailChangeConfirmation() {
        vm.dataLoading = true;
        var urlLink = $stateParams.link;
        if (urlLink === undefined || urlLink === null || urlLink === "") {
            $state.go("error", {'code': '404'});
        }
        securitySettingsChangeEmailModel.emailChangeConfirmation(urlLink).then(
            function (success) {
                vm.dataLoading = false;
                emailChangeConfirmationService.isEmailChanged().then(
                    function(result){//success
                        notify.set($filter('translate')("SecuritySettings.msg.isEmailChanged.success"), {type: 'success'});
                        $state.go("logout");
                    },
                    function(err){//fail
                        console.log(err);
                        notify.set($filter('translate')("SecuritySettings.msg.isEmailChanged.fail"), {type: 'error'});
                        $state.go("home");
                    }
                )

            }, function (error) {
                vm.dataLoading = false;
                var status = error.status;
                if (status !== undefined && status !== null && status !== "") {
                    $state.go("error", {'code': status});
                } else {
                    notify.set($filter('translate')("SecuritySettings.msg.fail"), {type: 'error'})
                }
            })
    };

    emailChangeConfirmation();

});
