app.controller('loginController', function ($rootScope, loginModel, $filter, $state, notify, languageService, userInitService, $sessionStorage) {

    var vm = this;

    vm.dataLoading = false;

    vm.loginData = {};

    function login() {
        vm.dataLoading = true;
        loginModel.login(vm.loginData).then(
            function (success) {
                vm.dataLoading = false;
                $sessionStorage.sessionId = success.headers('session_id');
                userInitService.init();
                $state.go('home');

                languageService.getUserLanguage().then(
                    function(result){//success
                        languageService.updateUserLanguage(result.data);
                        notify.set($filter('translate')('Login.message.success'), {type: 'success'});
                    },
                    function(err){//fail
                        console.log(err);
                        return err;
                    }
                );

            }, function (error) {

                vm.dataLoading = false;
                notify.set($filter('translate')('Login.message.fail'), {type: 'error'});
            });
    }
    vm.login = login;
});