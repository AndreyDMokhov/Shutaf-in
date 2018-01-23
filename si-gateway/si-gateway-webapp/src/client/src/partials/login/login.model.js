"use strict";
app.factory('loginModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    function login(params) {
        return  rest.one('/api/login/').customPOST(params);
    }
    function resendEmailRegistration(params) {
        return  rest.one('/api/login/resend/registration').customPOST(params);
    }

    return {
        login: login,
        resendEmailRegistration : resendEmailRegistration
    };
});