app.controller('userSettingsController', function (userSettingsModel, $filter, notify) {
    var vm = this;

    vm.userData = {};
    vm.user = {};

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

    function saveNewData() {
        userSettingsModel.saveNewData(vm.userData).then(
            function (success) {

                notify.set($filter('translate')('Users.message.save.success'), {type: 'success'});
                getCurrentUserData();
            }, function (error) {
                notify.set($filter('translate')('Users.message.save.fail'), {type:'error'});
                console.log(error);
            });
    }


    activate();

    vm.saveNewData = saveNewData;
    vm.getCurrentUserData = getCurrentUserData;

});