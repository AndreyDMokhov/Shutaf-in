app.controller('userRegistration', function (registrationModel, notify, $state, $filter, CACHED_LANGUAGE_ID) {

    var vm = this;
    vm.registrationData = {};

    vm.dataLoading = false;


    function registerUser() {
        console.log(vm.registrationData);
        vm.dataLoading = true;
        vm.registrationData.userLanguageId = parseInt(localStorage.getItem(CACHED_LANGUAGE_ID));
        registrationModel.registerUser(vm.registrationData).then(
            function (success) {
                vm.dataLoading = false;
                $state.go("home");
            }, function (error) {
                vm.dataLoading = false;
                notify.set($filter('translate')("Registration.form.msg.registrationFail"), {type: 'error'});
            })
    };

    vm.registerUser = registerUser;

});


