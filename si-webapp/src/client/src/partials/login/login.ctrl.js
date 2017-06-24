app.controller('loginController', function (loginModel, Restangular, $filter, $state, notify, $window) {

    var vm = this;

    vm.loginData = {};

    function login() {
        loginModel.login(vm.loginData).then(
            function (success) {

                localStorage.setItem("session_id", success.headers('session_id'));
                console.log(success.headers('Authorization'));
                notify.set($filter('translate')('Login.message.success'), {type: 'success'});
                $state.go('home');
            }, function (error) {
                console.log(error);
                notify.set($filter('translate')('Login.message.fail'), {type: 'error'});
            });
    }
    vm.login = login;
});