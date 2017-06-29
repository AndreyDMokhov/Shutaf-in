app.factory('securitySettingsModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/security-settings');
    });

    function changeEmail(params) {
        return  rest.one('/').customPOST(params);
    }

    return {
        changeEmail: changeEmail
    }


});