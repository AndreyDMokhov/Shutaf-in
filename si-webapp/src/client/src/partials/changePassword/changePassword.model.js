"use strict";
app.factory('changePasswordModel', function (Restangular, $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/api/users/password');
    });

    function changePassword(param) {
        rest.setDefaultHeaders({'session_id':$sessionStorage.sessionId});
        return  rest.one('/change').customPUT(param)
    }

    return{
        changePassword: changePassword
    }
});