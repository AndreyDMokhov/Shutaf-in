app.factory('palletModel', function (Restangular, $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        RestangularProvider.setBaseUrl('api/deal/documents');
    });


    function addDocument(param) {
        return rest.one('/').customPOST(param);
    }

    function deleteDocument(docId) {
        return rest.one('/' + docId).remove();
    }

    function renameDocument(docId, params) {
        return rest.one('/' + docId).customPOST(params);
    }

    function getDocument(docId) {
        return rest.one('/' + docId).get();
    }


    return {
        addDocument: addDocument,
        deleteDocument: deleteDocument,
        renameDocument: renameDocument,
        getDocument: getDocument
    };
});
