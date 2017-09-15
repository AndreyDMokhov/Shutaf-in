"use strict";
app.factory('logoutModel', function (Restangular, $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    function logout(params) {
        rest.setDefaultHeaders({'session_id':$sessionStorage.sessionId});
        return  rest.one('/api/logout/').customPOST(params);
    }

    return {
        logout:logout
    }
});