app.controller('userRegistration', function (registrationModel, notify, $state, $filter) {
    var vm = this;
    vm.registrationData = {};

    vm.dataLoading = false;


    function registerUser() {
        console.log(vm.registrationData);
        vm.dataLoading = true;
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


