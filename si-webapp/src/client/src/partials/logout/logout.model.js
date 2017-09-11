"use strict";
app.factory('logoutModel', function (Restangular, $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/api/logout');
    });

    function logout(params) {
        rest.setDefaultHeaders({'session_id':$sessionStorage.sessionId});
        return  rest.one('/').customPOST(params);
    }

    return {
        logout:logout
    }
});