"use strict";
app.component('userSettingsComponent', {
    templateUrl: 'partials/settings/userSettings/userSettings.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function ($localStorage,
                          $sessionStorage,
                          userSettingsModel,
                          notify,
                          $filter,
                          $state,
                          initializationService,
                          $window,
                          browserTitle) {

        browserTitle.setBrowserTitleByFilterName('UserSettings.personal.title');

        var vm = this;
        vm.dataLoading = false;
        vm.status = {
            isGeneralOpen: true,
            isAdditionalOpen: false
        };

        vm.userProfile = $sessionStorage.userProfile;
        vm.userProfile.dateOfBirth = new Date(vm.userProfile.dateOfBirth);
        vm.country = $sessionStorage.countries;
        vm.cities = $sessionStorage.cities;
        vm.gender = $sessionStorage.genders;

        function submitChanges() {
            vm.dataLoading = true;
            userSettingsModel.saveDataPostRegistration(vm.userProfile).then(
                function () {
                    vm.dataLoading = false;
                    notify.set($filter('translate')('UserSettings.message.save.success'), {type: 'success'});
                    $window.location.reload();
                    $state.go('userProfile', {id: $sessionStorage.userProfile.userId});
                }, function (error) {
                    vm.dataLoading = false;
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                    if (error.data.error.errorTypeCode === 'AUT') {
                        $state.go('logout');
                    }
                });
        }
        vm.submitChanges = submitChanges;
    }
});