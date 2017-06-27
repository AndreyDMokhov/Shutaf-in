app.factory('userSettingsModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        // RestangularProvider.setBaseUrl('/api/users');
    });


    function getUsers(params) {
        return rest.all('/api/users/').customGETLIST('', params);
    }

    function saveNewData(params) {
        // return rest.one('/api/userSettings/save').customPUT(params);
    //    ONLY TESTS
        rest.setDefaultHeaders({'session_id':localStorage.getItem('session_id')});
        return  rest.one('/api/userSettings/save').customPUT(params);
    }


    return {
        getUsers: getUsers,
        saveNewData: saveNewData
    }
});