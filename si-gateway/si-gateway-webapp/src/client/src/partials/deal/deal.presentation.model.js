"use strict";
app.factory('dealPresentationModel', function () {


    var deals = [
        {
            dealId: 0,
            title: 'My deal',
            statusId: 2
        },
        {
            dealId: 1,
            title: 'Archive',
            statusId: 3

        }];

    function getDeals() {
        return deals;
    }

    return {
        getDeals: getDeals
    };
});