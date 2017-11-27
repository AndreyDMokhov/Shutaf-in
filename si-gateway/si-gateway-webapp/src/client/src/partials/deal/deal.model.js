"use strict";
app.factory('dealModel', function (Restangular,
                                   $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/deal');
    });

    var palletes = [
        {
            palletId: 0,
            title: "Bills",
            content: {
                file: "file",
                file1: "file1"
            }
        },
        {
            palletId: 1,
            title: "Photos",
            content: {
                file: "photo",
                file1: "photo1"
            }
        }

    ];
    var tabs = [
        {
            tabId: 0,
            title: "Bills"
        },
        {
            tabId: 1,
            title: "Photos"
        }
    ];
    var deals =  [
        {
            dealId: 0,
            title: 'My deal'
        },
        {
            dealId: 1,
            title: 'Archive'
        }];

    function getDeals() {
        var promise = new Promise(function (resolve, err) {
            setTimeout(function () {
                resolve(deals);
            }, 500);
        });
        return promise;
    }

    function getTabs() {
        return tabs;
    }

    function getPallets() {
        return palletes;
    }


    function dealTabsGet(userId, dealId) {

        return rest.all('/get_deal_tabs/' + userId + '/' + dealId).getList();
    }


    function dealContentGet(userId, dealId, pallet) {
        return rest.all('/get_content_pallet_deals/' + userId + '/' + dealId + '/' + palletId).getList();

    }

    return {
        getDeals: getDeals,
        getTabs: getTabs,
        getPallets: getPallets,
        dealContentGet: dealContentGet,
        dealTabsGet: dealTabsGet
    };
});