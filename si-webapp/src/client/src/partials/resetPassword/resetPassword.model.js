app.factory('resetPassword', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/reset-password');
    });

    function requestResetPassword(params) {
        return rest.one('/request').customPOST(params);
    }

    function resetPasswordValidate(params) {
        var link = '/validate/'+params;
        return rest.one(link).customGET()
    }

    function resetPasswordChange(params){
        var link = '/change/'+params.link;
        var PasswordWeb = {newPassword:params.newPassword};
        return rest.one(link).customPOST(PasswordWeb)
    }

    return {
        requestResetPassword: requestResetPassword,
        resetPasswordValidate: resetPasswordValidate,
        resetPasswordChange:resetPasswordChange
    }
});