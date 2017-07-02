app.controller('userSettingsController', function (userSettingsModel, $filter, notify) {
    var vm = this;

    vm.dataLoading = false;

    vm.user = {};
    vm.dataFormDb = {};

    function activate() {
        getCurrentUserData();
    }

    function getCurrentUserData() {

        userSettingsModel.getCurrentUserData().then(
            function (success) {
                vm.user = success;
            }, function (error) {
                console.log(error);
            });
    }

    function saveNewUserData() {
          vm.dataLoading = true;

            userSettingsModel.saveNewUserData(vm.user).then(
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