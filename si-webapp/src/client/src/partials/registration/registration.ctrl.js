app.controller('userRegistration', function (registrationModel, notify, $state, $filter, CACHED_LANGUAGE_ID, $sessionStorage) {

    var vm = this;
    vm.registrationData = {};

    vm.dataLoading = false;


    function registerUser() {
        console.log(vm.registrationData);
        vm.dataLoading = true;

        vm.registrationData.userLanguageId = $sessionStorage.currentLanguage.id;
        registrationModel.registerUser(vm.registrationData).then(
            function (success) {
                vm.dataLoading = false;
                $state.go("home");
            }, function (error) {
                vm.dataLoading = false;

                if (error.data.error.errorTypeCode === 'EDE') {

                    notify.set($filter('translate')("Registration.form.msg.emailDuplication"), {type: 'error'});
                } else {

                    notify.set($filter('translate')("Registration.form.msg.registrationFail"), {type: 'error'});
                }
            })
    };

    vm.registerUser = registerUser;

});


