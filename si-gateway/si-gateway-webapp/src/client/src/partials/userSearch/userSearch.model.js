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

    function saveFilters(params,fullName) {
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        if (fullName) {
            return rest.one('/api/users/search/save/filters?name=' + fullName).customPOST(params);
        }
        return rest.one('/api/users/search/save/filters').customPOST(params);

    }

    function getUserInfo(id) {
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        return rest.one('api/images/' + id).customGET();
    }

    function getUserImageById(userId){
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        return rest.one('api/images/compressed/' + userId).customGET();
    }

    function getOriginalUserImageById(userId){
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        return rest.one('api/images/original/' + userId).customGET();
    }

    return {
        userSearch: userSearch,
        saveFilters: saveFilters,
        getUserInfo: getUserInfo,
        getUserImageById:getUserImageById,
        getOriginalUserImageById:getOriginalUserImageById

    };
});