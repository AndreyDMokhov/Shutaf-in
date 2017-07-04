app.factory('userSettingsModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/api/usersettings');
        RestangularProvider.setDefaultHeaders({'session_id':localStorage.getItem('session_id')});
    });


    function getCurrentUserData(params) {
        return rest.all('/get').customGET('', params);
    }

    function saveNewUserData(params) {
        return  rest.one('/save').customPUT(params);
    }


    return {
        getCurrentUserData: getCurrentUserData,
        saveNewUserData: saveNewUserData
    }
});