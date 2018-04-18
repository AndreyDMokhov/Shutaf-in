"use strict";
app.factory('userSearchModel', function ($sessionStorage, Restangular) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);

    });

    function userSearch(fullName,page,results) {
        var showPage = (page === '' || page === undefined) ? 0 : page ;
        var pageSize = (results === '' || results === undefined) ? 10 : results ;
        if (fullName) {
            return rest.one('/api/users/search?page='+showPage+'&results='+pageSize+'&name=' + fullName).customGET();
        }
        return rest.one('/api/users/search?page='+showPage+'&results='+pageSize).customGET();
    }

    function saveFilters(params,fullName) {
        if (fullName) {
            return rest.one('/api/users/search/save/filters?page=0&results=10&name=' + fullName).customPOST(params);
        }
        return rest.one('/api/users/search/save/filters?page=0&results=10').customPOST(params);
    }

    function getCompressedUserImageById(userId) {
        return rest.one('api/images/' + userId).customGET();
    }

    function getOriginalUserImageById(userId){
        return rest.one('api/images/original/' + userId).customGET();
    }

    return {
        userSearch: userSearch,
        saveFilters: saveFilters,
        getCompressedUserImageById: getCompressedUserImageById,
        getOriginalUserImageById:getOriginalUserImageById

    };
});