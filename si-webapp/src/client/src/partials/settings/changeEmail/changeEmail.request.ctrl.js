"use strict";
app.controller('changeEmailRequestController', function ($state, $rootScope, changeEmailModel, notify, $filter, $window) {
    $window.document.title = "Shutaf-In | " +$state.current.title;
    var vm = this;
    vm.dataLoading = false;
    vm.isOpened = true;
    vm.securitySettings = {};

    function changeEmailRequest() {
        vm.dataLoading = true;

        changeEmailModel.emailChangeRequest(vm.securitySettings).then(
            function (success) {
                vm.dataLoading = false;
                notify.set($filter('translate')("Settings.security.msg.successRequest"), {type: 'success'});
                $state.go("home");
            }, function (error) {
                vm.dataLoading = false;
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                if (error.data.error.errorTypeCode === 'AUT') {
                    $state.go('logout');
                }

            });
    }

    vm.changeEmailRequest = changeEmailRequest;
});
