"use strict";
app.controller('registrationConfirmation', function (registrationModel,
                                                     notify,
                                                     $state,
                                                     $filter,
                                                     $stateParams,
                                                     $sessionStorage,
                                                     $translate,
                                                     initializationService,
                                                     $window) {

    function confirmUserRegistration() {
        var urlLink = $stateParams.link;
        if (urlLink === undefined || urlLink === null || urlLink === "") {
            $state.go("error", {'code': '404'});
        }
        registrationModel.confirmRegistration(urlLink).then(
            function (success) {

                $sessionStorage.sessionId = success.headers('session_id');
                $window.location.href = "/";
                notify.set($filter('translate')("Registration.form.msg.registration.success"), {type: 'success'});
                $state.go("home");
            }, function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                if (error.data.error.errorTypeCode === 'RNF') {
                    $state.go("error", {'code': '404'});
                }
            });
    }

    confirmUserRegistration();

});
