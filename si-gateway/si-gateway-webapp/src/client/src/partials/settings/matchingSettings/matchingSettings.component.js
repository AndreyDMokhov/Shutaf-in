"use strict";
app.component('matchingSettingsComponent', {
    templateUrl: 'partials/settings/matchingSettings/matchingSettings.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function ($sessionStorage,
                          siteAccessRouting,
                          browserTitleService,
                          accountStatus,
                          matchingModel) {
        var vm = this;
        browserTitleService.setBrowserTitleByFilterName('UserSetting.personal.matching.title');

        vm.switchStatus = $sessionStorage.isMathingEnabled;
        vm.isEnabledMatching = function () {

            return !($sessionStorage.accountStatus === accountStatus.Statuses.COMPLETED_REQUIRED_MATCHING);

        };

        vm.changeMatchingVisible = function () {

            matchingModel.saveMatchingStatus(vm.switchStatus).then(
                function (success) {
                    $sessionStorage.isMathingEnabled = vm.switchStatus;

                },
                function (error) {
                    vm.switchStatus = !vm.switchStatus;
                }
            );
        };

    }
});