"use strict";
app.controller('changeEmailConfirmationController', function ($state, $rootScope, changeEmailModel, notify, $filter, $stateParams) {

    var vm = this;

    vm.dataLoading = false;
    vm.securitySettings = {};

    function changeEmail() {
        vm.dataLoading = true;
        var urlLink = $stateParams.link;
        if (urlLink === undefined || urlLink === null || urlLink === "") {
            $state.go("error", {'code': '404'});
        }
        changeEmailModel.emailChangeConfirmation(urlLink).then(
            function (success) {
                vm.dataLoading = false;
                if (success.data.emailChanged === false) {

                    notify.set($filter('translate')("Settings.security.firstEmailConfirmed.success"), {type: 'success'});
                    $state.go('home');
                }

                if (success.data.emailChanged === true) {

                    notify.set($filter('translate')("Settings.security.secondEmailConfirmed.success"), {type: 'success'});
                    $state.go("logout");
                }
            }, function (error) {
                vm.dataLoading = false;

                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});

                if (error.data.error.errorTypeCode === 'RNF') {

                    $state.go("error", {'code': '404'});
                }
            });
    }

    changeEmail();

});
