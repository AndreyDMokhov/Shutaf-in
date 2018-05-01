"use strict";
app.component('loginComponent', {
    templateUrl: 'partials/login/login.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function (loginModel,
                          $filter,
                          $state,
                          uiNotification,
                          $sessionStorage,
                          initializationService,
                          $window,
                          browserTitleService,
                          accountStatus,
                          siteAccessRouting,
                          sessionStorageObserver,
                          authenticationService) {

        browserTitleService.setBrowserTitleByFilterName('Login.title');

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
                    authenticationService.setSessionId(success.headers('session_id'));
                    initializationService.initializeApplication().then(
                        function () {
                            var message = $filter('translate')('Login.message.success');
                            uiNotification.show(message, 'success');
                            siteAccessRouting.navigate('myUserProfile', {});
                            sessionStorageObserver.notifyServiceObservers();

                        }, function (error) {
                            vm.dataLoading = false;
                        });
                },
                function (error) {
                    if (error.data.error.errorTypeCode === "ANC") {
                        vm.showResend = true;
                    }
                    vm.dataLoading = false;
                });
        }

        function resendEmailRegistration() {
            vm.resendLoading = true;
            loginModel.resendEmailRegistration(vm.loginData).then(
                function (success) {
                    vm.resendLoading = false;
                    uiNotification.show($filter('translate')('Registration.request.success'), 'success');
                    siteAccessRouting.navigate('home', {});
                },
                function (error) {
                    if (error.data.error.errorTypeCode === "AUT") {
                        vm.showResend = false;
                    }
                    vm.resendLoading = false;
                }
            );
        }

        vm.login = login;
        vm.resendEmailRegistration = resendEmailRegistration;
    }
});