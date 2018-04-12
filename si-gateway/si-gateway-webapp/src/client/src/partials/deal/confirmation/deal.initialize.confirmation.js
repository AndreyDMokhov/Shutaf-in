"use strict";
app.controller('dealInitializeConfirmation', function (dealConfirmationModel,
                                                       notify,
                                                       $state,
                                                       $filter,
                                                       $stateParams) {

    function confirmDealInitialization() {
        var urlLink = $stateParams.link;
        if (urlLink === undefined || urlLink === null || urlLink === "") {
            $state.go("error", {'code': '404'});
        }
        dealConfirmationModel.initialize(urlLink).then(
            function (success) {
                notify.set($filter('translate')("Deal.confirmation.initialization", {dealName: success.data.data.title}), {type: 'success'});
                $state.go("home");
            }, function (error) {}
            );
    }

    confirmDealInitialization();

});
