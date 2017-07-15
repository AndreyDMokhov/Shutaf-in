app.factory('securitySettingsModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/api/account/security-settings');
        RestangularProvider.setDefaultHeaders({'session_id':localStorage.getItem('session_id')});
    });

    function getEmail(param) {
        return rest.one('/getEmail').customGET('', param);
    }

    function updateEmail(param) {

        return  rest.one('/email').customPUT('',param)
    }
    function updatePassword(param) {

        return  rest.one('/password').customPUT('',param)
    }

    return {
        updateEmail:updateEmail,
        updatePassword:updatePassword,
        getUser:getUser
    }
});