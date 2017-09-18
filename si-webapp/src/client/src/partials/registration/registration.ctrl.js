app.controller('userRegistration', function (registrationModel, notify, $state, $filter, CACHED_LANGUAGE_ID, $sessionStorage) {

    var vm = this;
    vm.registrationData = {};

    vm.dataLoading = false;


    function registerUser() {
        vm.dataLoading = true;

        vm.registrationData.userLanguageId = $sessionStorage.currentLanguage;
        registrationModel.registerUser(vm.registrationData).then(
            function (success) {
                vm.dataLoading = false;
                notify.set($filter('translate')('Registration.request.success'));
                $state.go("home");
            }, function (error) {
                vm.dataLoading = false;

                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
            })
    };

    vm.registerUser = registerUser;

});