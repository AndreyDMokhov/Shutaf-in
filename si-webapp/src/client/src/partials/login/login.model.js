app.factory('loginModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    function login(params) {
        return  rest.one('/api/login/').customPOST(params);
    }

    return {
        login: login
    }
});