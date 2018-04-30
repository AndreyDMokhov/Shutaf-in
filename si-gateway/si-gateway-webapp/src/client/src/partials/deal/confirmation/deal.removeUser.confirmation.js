"use strict";
app.controller('dealRemoveUserConfirmation', function (dealConfirmationModel,
                                                       uiNotification,
                                                       $state,
                                                       $filter,
                                                       $stateParams) {

    function confirmRemoveDealUser() {
        var urlLink = $stateParams.link;
        if (urlLink === undefined || urlLink === null || urlLink === "") {
            $state.go("error", {'code': '404'});
        }
        dealConfirmationModel.removeUser(urlLink).then(
            function (success) {
                uiNotification.show($filter('translate')("Deal.confirmation.removeUser"), 'success');
                $state.go("home");
            }, function (error) {}
            );
    }

    confirmRemoveDealUser();

});
