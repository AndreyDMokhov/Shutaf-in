app.factory("userSettingsModel", function (Restangular, $sessionStorage) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    function saveDataPostRegistration(params) {
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        return rest.one('/api/users/settings/info/').customPOST(params);
    }

    return {
        saveDataPostRegistration: saveDataPostRegistration
    };
});

