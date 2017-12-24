"use strict";
app.factory('userSearchModel', function ($sessionStorage, Restangular) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    function userSearch(fullName) {
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        if (fullName) {

            return rest.one('/api/users/search?name=' + fullName).customGET();
        }
        return rest.one('/api/users/search').customGET();
    }

    function saveFilters(params) {
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        return rest.one('/api/users/search/save/filters').customPOST(params);
    }

    function getDefaultUserImageById(userId){
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        return rest.one('api/images/' + userId).customGET();
    }

    function getOriginalUserImageById(userId){
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        return rest.one('api/images/original/' + userId).customGET();
    }

    return {
        userSearch: userSearch,
        saveFilters: saveFilters,
        getDefaultUserImageById:getDefaultUserImageById,
        getOriginalUserImageById:getOriginalUserImageById

    };
});