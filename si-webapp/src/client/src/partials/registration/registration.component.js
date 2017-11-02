"use strict";
app.component('registrationComponent', {
    templateUrl: 'partials/registration/registration.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function (registrationService, notify, $state, $filter, CACHED_LANGUAGE_ID, $sessionStorage) {

        this.registrationData = {};
        this.dataLoading = false;

        function registerUser() {
            this.dataLoading = true;

            this.registrationData.userLanguageId = $sessionStorage.currentLanguage;
            registrationService.registerUser(this.registrationData).then(
                function (success) {
                    this.dataLoading = false;
                    notify.set($filter('translate')('Registration.request.success'));
                    $state.go("home");
                }, function (error) {
                    this.dataLoading = false;

                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                });
        }
        this.registerUser = registerUser;
}
});