app.factory("matchingModel", function (Restangular, $sessionStorage) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/users/matching');
        RestangularProvider.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
    });

    function saveMatchingStatus(params) {
        debugger;
        rest.setDefaultHeaders({'session_id':$sessionStorage.sessionId});
        return  rest.one('/configure').put(params);

    }

    return {
        saveMatchingStatus: saveMatchingStatus
    };
});
