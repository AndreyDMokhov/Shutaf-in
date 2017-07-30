app.factory('userSettingsModel', function (Restangular, $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/api/users/settings');
        RestangularProvider.setDefaultHeaders({'session_id':$sessionStorage.sessionId});
    });

    function submitChanges(params) {
        return  rest.one('/update').customPUT(params);
    }

    return {
        submitChanges: submitChanges
    }
});