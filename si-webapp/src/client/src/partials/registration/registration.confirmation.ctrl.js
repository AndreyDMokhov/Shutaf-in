/**
 * Created by evgeny on 7/10/2017.
 */
app.controller('registrationConfirmation', function (registrationConfirmationModel, notify, $state, $filter, userInitService, $stateParams, languageService) {

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
                localStorage.setItem("session_id", session_id);
                userInitService.init();
                languageService.getUserLanguage().then(
                    function(result){//success
                        languageService.updateUserLanguage(result.data);
                    },
                    function(err){//fail
                        console.log(err);
                        notify.set($filter('translate')("Error.unexpected.server.error"), {type: 'error'});
                    }
                );
                notify.set($filter('translate')("Registration.form.msg.registrationOK"), {type: 'success'});
                $state.go("home");
            }, function (error) {
                vm.dataLoading = false;
                var status = error.status;
                if (status!==undefined && status!==null && status!==""){
                    $state.go("error", {'code' : status});
                } else {
                    notify.set($filter('translate')("Registration.form.msg.registrationFail"), {type: 'error'});
                }
            })
    };

    confirmUserRegistration();

});


