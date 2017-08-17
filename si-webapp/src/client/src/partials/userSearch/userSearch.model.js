app.factory('userSearchModel', function ($sessionStorage, Restangular) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/api/users');
    });

    function userSearch(fullName) {
        rest.setDefaultHeaders({'session_id':$sessionStorage.sessionId});
        return  rest.one('/search?fullName='+fullName).customGET();
    }

    return {
        userSearch: userSearch
    }
})