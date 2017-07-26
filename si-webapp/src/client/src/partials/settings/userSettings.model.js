app.factory('userSettingsModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/api/users/settings');
        RestangularProvider.setDefaultHeaders({'session_id':localStorage.getItem('session_id')});
    });

    function submitChanges(params) {
        return  rest.one('/update').customPUT(params);
    }

    return {
        submitChanges: submitChanges
    }
});