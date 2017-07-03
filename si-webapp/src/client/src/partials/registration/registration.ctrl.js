app.controller('userRegistration', function ($rootScope, registrationModel, notify, $state, $filter, userInitService) {
    var vm = this;
    vm.registrationData = {};

    vm.dataLoading = false;


    function registerUser() {
        console.log(vm.registrationData);
        vm.dataLoading = true;
        registrationModel.registerUser(vm.registrationData).then(
            function (success) {
                vm.dataLoading = false;
                var session_id = success.headers('session_id')
                localStorage.setItem("session_id", session_id);
                userInitService.init();
                notify.set($filter('translate')("Registration.form.msg.registrationOK"), {type: 'success'});
                $state.go("home");
            }, function (error) {
                vm.dataLoading = false;
                notify.set($filter('translate')("Registration.form.msg.registrationFail"), {type: 'error'});
            })
    };

    vm.registerUser = registerUser;

});


