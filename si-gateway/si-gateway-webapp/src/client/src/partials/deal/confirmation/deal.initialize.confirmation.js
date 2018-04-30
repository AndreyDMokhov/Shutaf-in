"use strict";
app.controller('dealInitializeConfirmation', function (dealConfirmationModel,
                                                       uiNotification,
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
                var message = $filter('translate')("Deal.confirmation.initialization", {dealName: success.data.data.title});
                uiNotification.show(message, 'success');
                $state.go("home");
            }, function (error) {}
            );
    }

    confirmDealInitialization();

});
