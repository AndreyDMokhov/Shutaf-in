"use strict";
app.controller('dealAddUserConfirmation', function (dealConfirmationModel,
                                                    $state,
                                                    $filter,
                                                    $stateParams,
                                                    uiNotification) {

    function confirmAddDealUser() {
        var urlLink = $stateParams.link;
        if (urlLink === undefined || urlLink === null || urlLink === "") {
            $state.go("error", {'code': '404'});
        }
        dealConfirmationModel.addUser(urlLink).then(
            function (success) {
                uiNotification.show($filter('translate')("Deal.confirmation.addUser"), 'success');

                $state.go("home");
            }, function (error) {});
    }

    confirmAddDealUser();

});
