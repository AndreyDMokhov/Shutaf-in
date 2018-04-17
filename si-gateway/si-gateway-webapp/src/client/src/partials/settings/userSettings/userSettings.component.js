"use strict";
app.component('userSettingsComponent', {
    templateUrl: 'partials/settings/userSettings/userSettings.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function ($localStorage,
                          $sessionStorage,
                          userSettingsModel,
                          uiNotification,
                          $filter,
                          $state,
                          $window,
                          browserTitleService,
                          siteAccessRouting,
                          $timeout,
                          sessionStorageObserver) {

        browserTitleService.setBrowserTitleByFilterName('UserSettings.personal.title');

        var vm = this;
        vm.dataLoading = false;
        var time;
        var timeIsOver = false;

        vm.status = {
            isGeneralOpen: true,
            isAdditionalOpen: false
        };

        vm.overOpenCloseChangeStatus = function (tabName) {

            timeIsOver = false;
            time = $timeout(function () {
                if (tabName === "general") {
                    vm.status.isGeneralOpen = !vm.status.isGeneralOpen;
                }
                else if (tabName === "additional") {
                    vm.status.isAdditionalOpen = !vm.status.isAdditionalOpen;
                }
                timeIsOver = true;
            }, 600);
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