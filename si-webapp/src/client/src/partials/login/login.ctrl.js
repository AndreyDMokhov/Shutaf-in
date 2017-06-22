app.controller('loginController', function (loginModel, $filter, notify, $modal) {

    var vm = this;

    vm.loginData = {};

    vm.submit = function () {
         loginModel.login(vm.loginData).then(
            function (success) {

            }, function (error) {
                console.log(error);
            });
    }


    function logout() {

        logoutModel.logout().then(
            function (success) {

            }, function (error) {
                console.log(error);
            });
    }
});