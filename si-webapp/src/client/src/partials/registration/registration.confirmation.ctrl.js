app.controller('registrationConfirmation', function (
    registrationConfirmationModel,
    notify,
    $state,
    $filter,
    userInitService,
    $stateParams,
    languageService,
    $sessionStorage,
    constantService,
    $translate,
    quizInitService,
    $window) {

    var vm = this;

    vm.dataLoading = false;


    function confirmUserRegistration() {
        vm.dataLoading = true;
        var urlLink = $stateParams.link;
        if (urlLink === undefined || urlLink === null || urlLink === "") {
            $state.go("error", {'code': '404'});
        }
        registrationConfirmationModel.confirmRegistration(urlLink).then(
            function (success) {
                vm.dataLoading = false;
                $sessionStorage.sessionId = success.headers('session_id');
                languageService.getUserLanguage().then(
                    function (result) {
                        $translate.use(result.data.description);
                    },
                    function (err) {
                        console.log(err);
                        notify.set($filter('translate')("Error.SYS"), {type: 'error'});
                    }
                );
                notify.set($filter('translate')("Registration.form.msg.registrationOK"), {type: 'success'});
                $window.location.reload();
                $state.go("userSettings");
            }, function (error) {
                vm.dataLoading = false;
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                if (error.data.error.errorTypeCode === 'RNF') {
                    $state.go("error", {'code': '404'});
                }
            })
    };

    confirmUserRegistration();

});


