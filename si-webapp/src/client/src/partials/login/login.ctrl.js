app.controller('loginController', function ($rootScope, loginModel, $filter, $state, notify, languageService, userInitService, constantService, quizInitService, $sessionStorage, $window) {

    var vm = this;

    vm.dataLoading = false;

    vm.loginData = {};

    function login() {
        vm.dataLoading = true;
        loginModel.login(vm.loginData).then(
            function (success) {
                vm.dataLoading = false;
                $sessionStorage.sessionId = success.headers('session_id');
                $window.location.reload();
                languageService.getUserLanguage().then(
                    function(result){
                        $translate.use(result.data.description);
                        notify.set($filter('translate')('Login.message.success'), {type: 'success'});
                    },
                    function(err){
                        console.log(err);
                        return err;
                    }
                );

                $state.go('home');

            }, function (error) {

                vm.dataLoading = false;
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
            });
    }
    vm.login = login;
});