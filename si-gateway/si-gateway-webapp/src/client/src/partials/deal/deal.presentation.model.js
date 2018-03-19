"use strict";
app.factory('dealPresentationModel', function (Restangular, $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        RestangularProvider.setBaseUrl('api/deal');
    });


    function getDeals() {

        return rest.one("/all").get();
    }

    function getDealInfo(dealId) {
        return rest.one('/' +dealId).customGET();
    }
    function renameDeal(dealId, params) {
        return rest.one('/rename/' +dealId).customPOST(params);
    }
    function initiateDeal(params){
        return rest.one('/').customPOST(params);
    }
    function leaveDeal(dealId){
        return rest.one('/leave/' +dealId).customPUT();
    }


    return {
        getDeals: getDeals,
        getDealInfo: getDealInfo,
        renameDeal: renameDeal,
        initiateDeal: initiateDeal,
        leaveDeal: leaveDeal

    };
});