app.factory('loginModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/login');
    });

    function login(params) {
        return  rest.one('/').customPOST(params);
    }

    return {
        login: login
    }
});