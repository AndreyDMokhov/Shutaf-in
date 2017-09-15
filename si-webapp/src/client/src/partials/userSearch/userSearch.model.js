"use strict";
app.factory('userSearchModel', function ($sessionStorage, Restangular) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    function userSearch(fullName) {
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        if (fullName) {

            return rest.one('/api/users/match/search?name=' + fullName).customGET();
        }
        return rest.one('/api/users/match/search').customGET();
    }

    return {
        userSearch: userSearch
    }
});