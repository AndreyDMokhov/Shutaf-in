app.controller('userSettingsController', function (userSettingsModel, $filter, notify) {
    var vm = this;

    vm.userData = {};
    vm.users = {};

    function activate() {
        getUserData();

    }

    function getUserData() {

        userSettingsModel.getUsers().then(
            function (success) {
                vm.users = success;
            }, function (error) {
                console.log(error);
            });
    }

    function saveNewData() {
        userSettingsModel.saveNewData(vm.userData).then(
            function (success) {

                notify.set($filter('translate')('Users.message.save.success'), {type: 'success'});
                getUserData();
            }, function (error) {
                notify.set($filter('translate')('Users.message.save.fail'), {type:'error'});
                console.log(error);
            });
    }


    activate();

    vm.saveNewData = saveNewData;
    vm.getUserData = getUserData;

});