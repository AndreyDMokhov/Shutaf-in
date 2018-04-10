"use strict";
app.controller('dealAddUserConfirmation', function (dealConfirmationModel,
                                                    notify,
                                                    $state,
                                                    $filter,
                                                    $stateParams) {

    function confirmAddDealUser() {
        var urlLink = $stateParams.link;
        if (urlLink === undefined || urlLink === null || urlLink === "") {
            $state.go("error", {'code': '404'});
        }
        dealConfirmationModel.addUser(urlLink).then(
            function (success) {

                notify.set($filter('translate')("Deal.confirmation.addUser"), {type: 'success'});
                $state.go("home");
            }, function (error) {});
    }

    confirmAddDealUser();

});
