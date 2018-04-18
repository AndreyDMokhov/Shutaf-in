"use strict";
app.factory('dealPresentationModel', function (Restangular, $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
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

    function getAvailableUsers(usersIds) {
        return rest.one('/available-users?users=' + usersIds).customGET();
    }

    function addUsersToDeal(dealId, userId) {
        return rest.one('/add/' + dealId + '/' + userId).customGET();

    }


    return {
        getDeals: getDeals,
        getDealInfo: getDealInfo,
        renameDeal: renameDeal,
        initiateDeal: initiateDeal,
        leaveDeal: leaveDeal,
        getAvailableUsers: getAvailableUsers,
        addUsersToDeal: addUsersToDeal

    };
});