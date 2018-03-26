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
                debugger;
                notify.set($filter('translate')("Deal.confirmation.initialization", {dealName: success.data.data.title}), {type: 'success'});
                $state.go("home");
            }, function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                if (error.data.error.errorTypeCode === 'RNF') {
                    $state.go("error", {'code': '404'});
                }
            });
    }

    confirmDealInitialization();

});
