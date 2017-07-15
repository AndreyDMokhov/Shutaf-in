app.factory('registrationModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/users');
    });

    function registerUser(params) {
        return rest.one('/registration/request').customPOST(params);
    }

    return {
        registerUser: registerUser
    }
});