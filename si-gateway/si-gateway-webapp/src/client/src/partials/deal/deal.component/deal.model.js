"use strict";
app.factory('dealModel', function (Restangular, $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        RestangularProvider.setBaseUrl('api/deal/');
    });

    function getPanel(panelId) {
        return rest.one('panel/' + panelId).get();
    }

    function addPanel(param) {
        return rest.one('panel/').customPOST(param);
    }

    function removePanel(param) {
        return rest.one('panel/'  + param.panelId).remove();
    }

    function renamePanel(panelId, param) {
        return rest.one('panel/' + panelId).customPOST(param);
    }

    return {
        getPanel: getPanel,
        addPanel: addPanel,
        removePanel: removePanel,
        renamePanel: renamePanel

    };
});