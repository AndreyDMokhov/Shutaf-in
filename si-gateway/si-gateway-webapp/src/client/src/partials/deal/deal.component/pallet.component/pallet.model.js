app.factory('palletModel', function (Restangular, $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        RestangularProvider.setBaseUrl('api/deal/documents');
    });


    function addDocument(param) {
        return  rest.one().customPOST(param);
    }

    return {
        addDocument: addDocument,
        getDocuments: getDocuments
    };
});
