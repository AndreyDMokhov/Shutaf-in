app.controller('userRegistration', function (registrationModel, notify, $state, $filter) {
    var vm = this;
    vm.registrationData = {};


    function registerUser() {
        console.log(vm.registrationData);
        registrationModel.registerUser(vm.registrationData).then(
            function (success) {
                localStorage.setItem("session_Id", success.headers('session_id'));
                notify.set($filter('translate')("Registration.form.msg.registrationOK"), {type: 'success'});
                $state.go("home");
            }, function (error) {
                notify.set($filter('translate')("Registration.form.msg.registrationFail"), {type: 'error'});
            })
    };

    vm.registerUser = registerUser;

});


