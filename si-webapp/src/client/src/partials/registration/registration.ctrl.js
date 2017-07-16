app.controller('userRegistration', function ($rootScope, registrationModel, notify, $state, $filter, userInitService, CACHED_LANGUAGE_ID) {

    var vm = this;
    vm.registrationData = {};

    vm.dataLoading = false;


    function registerUser() {
        vm.dataLoading = true;

        vm.registrationData.userLanguageId = localStorage.getItem(CACHED_LANGUAGE_ID);
        registrationModel.registerUser(vm.registrationData).then(
            function (success) {
                vm.dataLoading = false;
                var session_id = success.headers('session_id');
                localStorage.setItem("session_id", session_id);
                userInitService.init();
                notify.set($filter('translate')("Registration.form.msg.registrationOK"), {type: 'success'});
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


