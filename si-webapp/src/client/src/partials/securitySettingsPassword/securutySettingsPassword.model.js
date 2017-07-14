app.factory('securitySettingsPasswordModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/api/account/security-settings/password');
        RestangularProvider.setDefaultHeaders({'session_id':localStorage.getItem('session_id')});
    });

    function updatePassword(param) {

        return  rest.one('/password').customPUT(param)
    };

    return{
        updatePassword: updatePassword
    }
});