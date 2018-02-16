"use strict";
app.component('loginComponent', {
    templateUrl: 'partials/login/login.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function (loginModel,
                          $filter,
                          $state,
                          notify,
                          $sessionStorage,
                          initializationService,
                          $window,
                          browserTitle,
                          accountStatus,
                          siteAccessRouting) {

        browserTitle.setBrowserTitleByFilterName('Login.title');

        var vm = this;
        vm.dataLoading = false;
        vm.resendLoading = false;
        vm.showResend = false;
        vm.loginData = {};

        function login() {
            vm.dataLoading = true;
            loginModel.login(vm.loginData).then(
                function (success) {
                    vm.dataLoading = false;
                    $sessionStorage.sessionId = success.headers('session_id');
                    initializationService.initializeApplication().then(
                        function () {
                            $window.location.reload();
                            notify.set($filter('translate')('Login.message.success'), {type: 'success'});
                            siteAccessRouting.navigate('home', {});

                        }, function (error) {
                            vm.dataLoading = false;
                            notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                        });
                },
                function (error) {
                    if (error.data.error.errorTypeCode === "ANC") {
                        vm.showResend = true;
                    }
                    vm.dataLoading = false;
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                });
        }

        function resendEmailRegistration() {
            vm.resendLoading = true;
            loginModel.resendEmailRegistration(vm.loginData).then(
                function (success) {
                    vm.resendLoading = false;
                    notify.set($filter('translate')('Registration.request.success'), {type: 'success'});
                    siteAccessRouting.navigate('home', {});
                },
                function (error) {
                    if (error.data.error.errorTypeCode === "AUT") {
                        vm.showResend = false;
                    }
                    vm.resendLoading = false;
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                }
            );
        }

        vm.login = login;
        vm.resendEmailRegistration = resendEmailRegistration;
    }
});