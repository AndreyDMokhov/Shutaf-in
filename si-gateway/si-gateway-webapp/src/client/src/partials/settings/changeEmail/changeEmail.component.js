"use strict";
app.component('changeEmailComponent', {
    templateUrl: 'partials/settings/changeEmail/changeEmail.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function (
        $state,
        $rootScope,
        changeEmailModel,
        $filter,
        browserTitleService,
        uiNotification) {

    browserTitleService.setBrowserTitleByFilterName('Settings.security.email.title');
        var vm = this;
        vm.dataLoading = false;
        vm.isOpened = true;
        vm.securitySettings = {};

        function changeEmailRequest() {
            vm.dataLoading = true;

            changeEmailModel.emailChangeRequest(vm.securitySettings).then(
                function (success) {
                    vm.dataLoading = false;
                    var message = $filter('translate')("Settings.security.msg.successRequest");
                    uiNotification.show(message, 'success');


                    $state.go("home");
                }, function (error) {
                    vm.dataLoading = false;

                });
        }

        vm.changeEmailRequest = changeEmailRequest;
    }
});
