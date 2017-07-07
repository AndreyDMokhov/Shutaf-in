app.controller('userRegistration', function (registrationModel, notify, $state, $filter, CACHED_LANGUAGE_ID) {
    var vm = this;
    vm.registrationData = {};

    vm.dataLoading = false;


    function registerUser() {
        console.log(vm.registrationData);
        vm.dataLoading = true;
        vm.registrationData[CACHED_LANGUAGE_ID] = localStorage.getItem(CACHED_LANGUAGE_ID);
        registrationModel.registerUser(vm.registrationData).then(
            function (success) {
                vm.dataLoading = false;
                localStorage.setItem("session_id", success.headers('session_id'));
                notify.set($filter('translate')("Registration.form.msg.registrationOK"), {type: 'success'});
                $state.go("home");
            }, function (error) {
                vm.dataLoading = false;
                notify.set($filter('translate')("Registration.form.msg.registrationFail"), {type: 'error'});
            })
    };

    vm.registerUser = registerUser;

});


