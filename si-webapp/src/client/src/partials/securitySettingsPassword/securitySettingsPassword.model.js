app.factory('securitySettingsPasswordModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/api/users/password');
    });

    function changePassword(param) {
        rest.setDefaultHeaders({'session_id':localStorage.getItem('session_id')});
        return  rest.one('/change').customPUT(param)
    }

    return{
        changePassword: changePassword
    }
});