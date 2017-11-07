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
                          browserTitle) {

        browserTitle.setBrowserTitleByFilterName('Login.title');

        var vm = this;
        vm.dataLoading = false;

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
                            $state.go('home');
                        }, function (error) {
                            vm.dataLoading = false;
                            notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                        });
                    $state.go('home');
                },
                function (error) {

                    vm.dataLoading = false;
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                });
        }

        vm.login = login;
    }
});