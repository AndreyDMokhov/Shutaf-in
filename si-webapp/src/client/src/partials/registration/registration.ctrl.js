app.controller('userRegistration', function (registrationModel, $http, notify, $state) {
    var vm = this;
    vm.registrationData = {};

    function registerUser() {
        console.log(vm.registrationData);
        registrationModel.registerUser(vm.registrationData).then(
            function (success) {
                localStorage.setItem("session_Id", success.headers('session_id'));
                notify.set('Registration is ok', {type: 'success'});
                $state.go("home");
            }, function (error) {
                notify.set("Registration fail", {type: 'error'});
            })
    };

    vm.registerUser = registerUser;

});


