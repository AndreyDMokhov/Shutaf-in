"use strict";
app.factory('settingsModel', function (Restangular, $sessionStorage) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/api/users/settings');
    });
    function submitChanges(params) {
        rest.setDefaultHeaders ({'session_id':$sessionStorage.sessionId});
        return  rest.one('/update').customPUT(params);
    }

    return {
        submitChanges: submitChanges
    }
});