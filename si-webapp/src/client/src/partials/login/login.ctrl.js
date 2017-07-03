app.controller('loginController', function (loginModel, $filter, $state, notify, userInitService) {

    var vm = this;

    vm.dataLoading = false;

    vm.loginData = {};

    function login() {
        vm.dataLoading = true;
        loginModel.login(vm.loginData).then(
            function (success) {
                vm.dataLoading = false;
                var session_id = success.headers('session_id')
                localStorage.setItem("session_id", success.headers('session_id'));
                    userInitService.init(session_id);

                notify.set($filter('translate')('Login.message.success'), {type: 'success'});
                $state.go('home');
            }, function (error) {

                vm.dataLoading = false;
                notify.set($filter('translate')('Login.message.fail'), {type: 'error'});
            });
    }
    vm.login = login;
});