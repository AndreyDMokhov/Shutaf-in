/**
 * Created by evgeny on 7/10/2017.
 */
app.controller('registrationConfirmation', function (registrationConfirmationModel, quizInitService, notify, $state, $filter, userInitService, $stateParams, languageService, $sessionStorage, constantService, $translate) {

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
                languageService.getUserLanguage().then(
                    function(result){//success
                        $translate.use(result.data.description);
                    },
                    function(err){//fail
                        console.log(err);
                        notify.set($filter('translate')("Error.SYS"), {type: 'error'});
                    }
                );

                userInitService.init().then(function () {
                    constantService.init();
                    quizInitService.init();
                });
                notify.set($filter('translate')("Registration.form.msg.registrationOK"), {type: 'success'});
                $state.go("userSettings");
            }, function (error) {
                vm.dataLoading = false;
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                if (error.data.error.errorTypeCode === 'RNF') {
                    $state.go("error", {'code' : '404'});
                }
            })
    };

    confirmUserRegistration();

});


