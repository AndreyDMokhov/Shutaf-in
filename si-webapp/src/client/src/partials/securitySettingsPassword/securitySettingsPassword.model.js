app.factory('securitySettingsPasswordModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/api/account/security-settings');
        RestangularProvider.setDefaultHeaders({'session_id':localStorage.getItem('session_id')});
    });

    function changePassword(param) {
        return  rest.one('/password').customPUT(param)
    }

    return{
        changePassword: changePassword
    }
});