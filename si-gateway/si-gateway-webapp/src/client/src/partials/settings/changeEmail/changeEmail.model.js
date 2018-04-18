"use strict";
app.factory('changeEmailModel', function (Restangular, $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    function emailChangeRequest(params) {
        return  rest.one('/api/users/account/change-email-request').customPOST(params);
    }
    function emailChangeConfirmation(urlLink) {
        return  rest.one('/api/users/account/change-email-confirmation/'+urlLink).customGET();
    }

    return{
        emailChangeRequest: emailChangeRequest,
        emailChangeConfirmation: emailChangeConfirmation
    };
});