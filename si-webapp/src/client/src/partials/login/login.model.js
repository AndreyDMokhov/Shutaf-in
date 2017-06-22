app.factory('loginModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/api/login');
    });

    function login(params) {
        return rest.one('/login').customPOST(params);
    }

    function logout() {
        return rest.one('/logout').customPOST();
    }

    return {

        login: login,
        logout: logout
    }
});