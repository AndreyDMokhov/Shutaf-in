"use strict";
app.factory('registrationModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    function registerUser(params) {
        return rest.one('/api/users/registration/request').customPOST(params);
    }

    return {
        registerUser: registerUser
    }
});