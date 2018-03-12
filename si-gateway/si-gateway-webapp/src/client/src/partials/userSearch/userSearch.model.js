"use strict";
app.factory('userSearchModel', function ($sessionStorage, Restangular) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);

    });

    function userSearch(fullName) {
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        if (fullName) {
            return rest.one('/api/users/search?page=1&results=10&name=' + fullName).customGET();
        }
        return rest.one('/api/users/search?page=1&results=10').customGET();
    }

    function saveFilters(params,fullName) {
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        if (fullName) {
            return rest.one('/api/users/search/save/filters?name=' + fullName).customPOST(params);
        }
        return rest.one('/api/users/search/save/filters').customPOST(params);
    }

    function getCompressedUserImageById(userId) {
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
        getCompressedUserImageById: getCompressedUserImageById,
        getOriginalUserImageById:getOriginalUserImageById

    };
});