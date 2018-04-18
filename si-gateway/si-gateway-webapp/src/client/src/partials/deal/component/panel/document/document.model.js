app.factory('documentModel', function (Restangular, $sessionStorage) {


    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('api/deal/documents');
    });

    function addDocument(param) {
      return  rest.one().customPOST(param);
    }

    function getDocument(docId) {
        return  rest.one().get(docId);
    }

    return {
        addDocument: addDocument,
        getDocument: getDocument
    };
});

