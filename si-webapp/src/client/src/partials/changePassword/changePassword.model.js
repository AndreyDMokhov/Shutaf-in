app.factory('changePasswordModel', function (Restangular, $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    function changePassword(param) {
        rest.setDefaultHeaders({'session_id':$sessionStorage.sessionId});
        return  rest.one('/api/users/password/change').customPUT(param)
    }

    return{
        changePassword: changePassword
    }
});