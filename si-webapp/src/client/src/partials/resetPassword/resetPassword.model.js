"use strict";
app.factory('resetPasswordModel', function (Restangular, $state, $window) {
    $window.document.title = "Shutaf-In | " +$state.current.title;
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    function requestResetPassword(params) {
        return rest.one('/api/reset-password/request').customPOST(params);
    }

    function resetPasswordValidate(params) {
        var link = '/api/reset-password/validate/'+params;
        return rest.one(link).customGET();
    }

    function resetPasswordChange(params){
        var link = '/api/reset-password/change/'+params.link;
        var PasswordWeb = {newPassword:params.newPassword};
        return rest.one(link).customPOST(PasswordWeb);
    }

    return {
        requestResetPassword: requestResetPassword,
        resetPasswordValidate: resetPasswordValidate,
        resetPasswordChange:resetPasswordChange
    };
});