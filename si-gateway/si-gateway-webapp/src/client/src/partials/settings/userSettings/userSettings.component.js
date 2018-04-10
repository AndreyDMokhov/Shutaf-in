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
                          browserTitle,
                          siteAccessRouting,
                          $timeout) {

        browserTitle.setBrowserTitleByFilterName('UserSettings.personal.title');

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


        vm.userProfile = $sessionStorage.userProfile;
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
                    notify.set($filter('translate')('UserSettings.message.save.success'), {type: 'success'});
                    initializationService.initialize(success.data);
                    $window.location.reload();

                    siteAccessRouting.navigate('userProfile', {id: $sessionStorage.userProfile.userId});

                }, function (error) {
                    vm.dataLoading = false;
                });
        }
        vm.submitChanges = submitChanges;
    }
});