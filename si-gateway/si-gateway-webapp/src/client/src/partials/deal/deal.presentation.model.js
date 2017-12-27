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


    return {
        getDeals: getDeals,
        getDealInfo: getDealInfo

    };
});