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
        console.log(dealId);
        console.log(params);
        return rest.one('/rename/' +dealId).customPOST(params);
    }


    return {
        getDeals: getDeals,
        getDealInfo: getDealInfo,
        renameDeal: renameDeal

    };
});