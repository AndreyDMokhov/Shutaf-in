"use strict";
app.controller('registrationConfirmation', function (registrationModel,
                                                     uiNotification,
                                                     $state,
                                                     $filter,
                                                     $stateParams) {

    function confirmUserRegistration() {
        var urlLink = $stateParams.link;
        if (urlLink === undefined || urlLink === null || urlLink === "") {
            $state.go("error", {'code': '404'});
        }
        registrationModel.confirmRegistration(urlLink).then(
            function (success) {
                uiNotification.show($filter('translate')('Registration.form.msg.registration.success'), 'success');
                $state.go('login');
            }, function (error) {});
    }

    confirmUserRegistration();

});
