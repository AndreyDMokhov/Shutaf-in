"use strict";
app.component('matchingSettingsComponent', {
    templateUrl: 'partials/settings/matchingSettings/matchingSettings.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function ($sessionStorage,
                          siteAccessRouting,
                          browserTitle,
                          accountStatus,
                          matchingModel,
                          notify,
                          $filter ) {
        var vm = this;
        browserTitle.setBrowserTitleByFilterName('UserSetting.personal.matching.title');
        vm.isOpened = true;
        vm.switchStatus = $sessionStorage.isMathingEnabled;
        console.log($sessionStorage);
        vm.isAccessRoute = function () {

            if ($sessionStorage.accountStatus === accountStatus.Statuses.COMPLETED_REQUIRED_MATCHING) {
                return false;
            } else {
                return true;
            }

        };

        vm.changeMatchingVisible = function () {
            var param = {
                enabled: vm.switchStatus
            };

            matchingModel.saveMatchingStatus(param).then(
                function (success) {
                    $sessionStorage.isMathingEnabled = vm.switchStatus;
                    console.log(success);
                },
                function (error) {
                    vm.switchStatus = !vm.switchStatus;
                    if (error === undefined || error === null) {
                        notify.set($filter('translate')('Error.SYS'), {type: 'error'});
                    }
                    notify.set($filter('translate')('Error' + '.' + error.status), {type: 'error'});
                    console.log(error);
                }
            );
        };

    }
});