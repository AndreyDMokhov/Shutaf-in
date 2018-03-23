"use strict";
app.controller('dealRemoveUserConfirmation', function (dealConfirmationModel,
                                                       notify,
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

                notify.set($filter('translate')("Deal.confirmation.removeUser"), {type: 'success'});
                $state.go("home");
            }, function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                if (error.data.error.errorTypeCode === 'RNF') {
                    $state.go("error", {'code': '404'});
                }
            });
    }

    confirmRemoveDealUser();

});
