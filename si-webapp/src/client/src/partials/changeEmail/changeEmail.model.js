app.factory('securitySettingsChangeEmailModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/api/users/account');
    });

    function emailChangeRequest(params) {
        rest.setDefaultHeaders({'session_id':localStorage.getItem('session_id')});
        return  rest.one('/change-email-request').customPOST(params)
    }
    function emailChangeConfirmation(urlLink) {
        rest.setDefaultHeaders({'session_id':localStorage.getItem('session_id')});
        return  rest.one('/change-email-confirmation/'+urlLink).customGET()
    }

    return{
        emailChangeRequest: emailChangeRequest,
        emailChangeConfirmation: emailChangeConfirmation
    }
});