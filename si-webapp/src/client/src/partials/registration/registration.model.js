app.factory('registrationModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/api/users');
    });

    function registerUser(params) {
        return rest.one('/registration').customPOST(params);
    }

    return {
        registerUser: registerUser
    }
});