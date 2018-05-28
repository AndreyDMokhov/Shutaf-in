"use strict";
app.component('userSettingsComponent', {
    templateUrl: 'partials/settings/userSettings/userSettings.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function ($sessionStorage,
                          userSettingsModel,
                          uiNotification,
                          $filter,
                          $state,
                          browserTitleService,
                          siteAccessRouting,
                          $timeout,
                          sessionStorageObserver,
                          initializationService) {

        browserTitleService.setBrowserTitleByFilterName('UserSettings.personal.title');

        var vm = this;
        vm.dataLoading = false;
        var time;
        var timeIsOver = false;


        vm.status = {
            isGeneralOpen: true,
            isAdditionalOpen: false,
            isPhotoOpen: false
        };



        vm.leaveOpenCloseChangeStatus = function () {
            if (!timeIsOver) {
                $timeout.cancel(time);
            }
        };


        vm.userProfile = angular.copy($sessionStorage.userProfile);
        vm.userProfile.dateOfBirth = new Date(vm.userProfile.dateOfBirth);
        vm.country = $sessionStorage.countries;
        vm.cities = $sessionStorage.cities;
        vm.gender = $sessionStorage.genders;

        function submitChanges() {
            vm.dataLoading = true;
            vm.userProfile.dateOfBirth = new Date(vm.userProfile.dateOfBirth).getTime();
            userSettingsModel.saveDataPostRegistration(vm.userProfile).then(
                function (success) {
                    vm.dataLoading = false;

                    var message = $filter('translate')('UserSettings.message.save.success');
                    $sessionStorage.userProfile = vm.userProfile;
                    initializationService.initialize(success.data);

                    sessionStorageObserver.notifyServiceObservers();

                    uiNotification.show(message, 'success');

                    siteAccessRouting.navigate('myUserProfile');

                }, function (error) {
                    vm.dataLoading = false;
                });
        }
        vm.submitChanges = submitChanges;
    }
});