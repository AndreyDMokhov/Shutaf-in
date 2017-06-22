app.controller('usersController', function (usersModel, $filter, notify, $modal) {
    var vm = this;

    vm.userData = {};
    vm.users = {};

    function activate() {
        getUserData();

    }

    function getUserData() {

        usersModel.getUsers().then(
            function (success) {
                vm.users = success;
            }, function (error) {
                console.log(error);
            });
    }

    function saveUserData() {
        usersModel.saveUser(vm.userData).then(
            function (success) {

                notify.set($filter('translate')('Users.message.save.success'), {type: 'success'});
                getUserData();
            }, function (error) {
                notify.set($filter('translate')('Users.message.save.fail'), {type:'error'});
                console.log(error);
            });
    }

    function updateUserData() {
        usersModel.updateUser().then(
            function (success) {
                notify.set($filter('translate')('Users.message.update.success'), {type: 'success'});
                getUserData();
            }, function (error) {
                notify.set($filter('translate')('Users.message.update.fail'), {type:'error'});
                console.log(error);
            });
    }

    function deleteUserData(id) {
        usersModel.deleteUser(id).then(
            function (success) {
                notify.set($filter('translate')('Users.message.delete.success'), {type: 'success'});
                getUserData();
            }, function (error) {
                notify.set($filter('translate')('Users.message.delete.fail'), {type:'error'});
                console.log(error);
            });
    }

    activate();

    vm.deleteUserData = deleteUserData;
    vm.updateUserData = updateUserData;
    vm.saveUserData = saveUserData;
    vm.getUserData = getUserData;

});