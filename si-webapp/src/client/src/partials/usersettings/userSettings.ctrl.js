app.controller('userSettingsController', function (userSettingsModel, languageService, CACHED_LANGUAGE, $filter, notify) {
    var vm = this;

    vm.dataLoading = false;

    vm.accountSettings = {};

    function activate() {
        getCurrentUserData();
    }

    function getCurrentUserData() {

        userSettingsModel.getCurrentUserData().then(
            function (success) {
                vm.accountSettings = success;
                languageService.setLanguage(vm.accountSettings.languageId);
            }, function (error) {
                console.log(error);
            });
    }

    function saveNewUserData() {
          vm.dataLoading = true;
        vm.accountSettings.languageId = localStorage.getItem(CACHED_LANGUAGE);
            userSettingsModel.saveNewUserData(vm.accountSettings).then(
                function (success) {
                    vm.dataLoading = false;

                    notify.set($filter('translate')('UserSettings.message.save.success'), {type: 'success'});
                    getCurrentUserData();
                }, function (error) {
                    vm.dataLoading = false;

                    notify.set($filter('translate')('UserSettings.message.save.fail'), {type: 'error'});
                    console.log(error);
                });

    }


    activate();

    vm.saveNewUserData = saveNewUserData;
    vm.getCurrentUserData = getCurrentUserData;

});