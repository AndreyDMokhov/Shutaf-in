/**
 * Created by evgeny on 7/10/2017.
 */
app.controller('registrationConfirmation', function (registrationConfirmationModel, notify, $state, $filter, userInitService, CACHED_LANGUAGE_ID, $stateParams) {

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
                notify.set($filter('translate')("Registration.form.msg.registrationOK"), {type: 'success'});
                $state.go("home");
            }, function (error) {
                vm.dataLoading = false;
                notify.set($filter('translate')("Registration.form.msg.registrationFail"), {type: 'error'});
            })
    };

    confirmUserRegistration();

});


