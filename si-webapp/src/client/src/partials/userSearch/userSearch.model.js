app.factory('userSearchModel', function ($sessionStorage, Restangular) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    function userSearch(fullName) {
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        return rest.one('/api/users/search?name=' + fullName).customGET();
    }

    return {
        userSearch: userSearch
    }
});