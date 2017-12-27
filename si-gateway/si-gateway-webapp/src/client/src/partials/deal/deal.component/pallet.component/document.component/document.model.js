app.factory('documentModel', function (Restangular, $sessionStorage) {


    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        RestangularProvider.setBaseUrl('api/deal/documents');
    });

    function addDocument(param) {
      return  rest.one().customPOST(param);
    }

    function getDocument(docId) {

    }

    return {
        addDocument: addDocument,
        getDocument: getDocument
    };
});

