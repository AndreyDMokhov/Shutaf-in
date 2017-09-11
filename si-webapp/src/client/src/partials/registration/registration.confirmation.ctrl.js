/**
 * Created by evgeny on 7/10/2017.
 */
"use strict";

app.controller('registrationConfirmation', function (registrationConfirmationModel, notify, $state, $filter, userInitService, $stateParams, languageService, $sessionStorage, constantService) {

    var vm = this;

    vm.dataLoading = false;


    function confirmUserRegistration() {
        vm.dataLoading = true;
        var urlLink = $stateParams.link;
        if (urlLink===undefined || urlLink===null || urlLink===""){
            $state.go("error", {'code' : '404'});
        }
        registrationConfirmationModel.confirmRegistration(urlLink).then(
            function (success) {
                vm.dataLoading = false;
                var session_id = success.headers('session_id');
                $sessionStorage.sessionId = session_id;
                userInitService.init();
                constantService.init();
                languageService.getUserLanguage().then(
                    function(result){//success
                        languageService.updateUserLanguage(result.data);
                    },
                    function(err){//fail
                        notify.set($filter('translate')("Error.SYS"), {type: 'error'});
                    }
                );
                notify.set($filter('translate')("Registration.form.msg.registrationOK"), {type: 'success'});
                $state.go("home");
            }, function (error) {
                vm.dataLoading = false;
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                if (error.data.error.errorTypeCode === 'RNF') {
                    $state.go("error", {'code' : '404'});
                }
            })
    }

    confirmUserRegistration();

});


