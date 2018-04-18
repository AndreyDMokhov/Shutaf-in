app.factory('panelModel', function (Restangular, $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('api/deal/documents');
    });

    function getPanel(panelId) {
        return rest.one('panel/' + panelId).get();
    }

    function addPanel(param) {
        return rest.one('panel/').customPOST(param);
    }

    function removePanel(param) {
        return rest.one('panel/' + param.panelId).remove();
    }

    function renamePanel(panelId, param) {
        return rest.one('panel/' + panelId).customPOST(param);
    }

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
        getPanel: getPanel,
        addPanel: addPanel,
        removePanel: removePanel,
        renamePanel: renamePanel,

        addDocument: addDocument,
        deleteDocument: deleteDocument,
        renameDocument: renameDocument,
        getDocument: getDocument
    };
});
