"use strict";
app.component('registrationComponent', {
    templateUrl: 'partials/registration/registration.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function (
        registrationModel,
        uiNotification,
        $state,
        $filter,
        CACHED_LANGUAGE_ID,
        $sessionStorage,
        browserTitleService) {

    browserTitleService.setBrowserTitleByFilterName('Registration.title.sign-up');
        var vm = this;
        vm.registrationData = {};

        vm.dataLoading = false;


        function registerUser() {
            vm.dataLoading = true;

            vm.registrationData.userLanguageId = $sessionStorage.currentLanguage;
            registrationModel.registerUser(vm.registrationData).then(
                function (success) {
                    vm.dataLoading = false;
                    uiNotification.show($filter('translate')('Registration.request.success'));
                    $state.go("home");
                }, function (error) {
                    vm.dataLoading = false;
                });
        }

        vm.registerUser = registerUser;
}
});