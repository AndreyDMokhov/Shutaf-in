app.controller('loginController', function ($rootScope, loginModel, $filter, $state, notify, userInitService, $q) {

    var vm = this;

    vm.dataLoading = false;

    vm.loginData = {};

    function login() {
        vm.dataLoading = true;
        loginModel.login(vm.loginData).then(
            function (success) {
                vm.dataLoading = false;
                localStorage.setItem("session_id", success.headers('session_id'));
                 userInitService.init();
                notify.set($filter('translate')('Login.message.success'), {type: 'success'});
                $state.go('home');
            }, function (error) {

                vm.dataLoading = false;
                notify.set($filter('translate')('Login.message.fail'), {type: 'error'});
            });
    }
    vm.login = login;
});