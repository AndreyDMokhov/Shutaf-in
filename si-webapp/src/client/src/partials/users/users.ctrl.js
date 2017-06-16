app.controller('usersController', function (usersModel, $filter) {
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

                alert($filter('translate')('Users.message.save.success'));
                getUserData();
            }, function (error) {

                alert($filter('translate')('Users.message.save.fail'));
                console.log(error);
            });
    }

    function updateUserData() {
        usersModel.updateUser().then(
            function (success) {

                alert($filter('translate')('Users.message.update.success'));
                getUserData();
            }, function (error) {

                alert($filter('translate')('Users.message.update.fail'));
                console.log(error);
            });
    }

    function deleteUserData(id) {
        usersModel.deleteUser(id).then(
            function (success) {
                alert($filter('translate')('Users.message.delete.success'));
                getUserData();
            }, function (error) {
                alert($filter('translate')('Users.message.delete.fail'));
                console.log(error);
            });
    }

    activate();

    vm.deleteUserData = deleteUserData;
    vm.updateUserData = updateUserData;
    vm.saveUserData = saveUserData;
    vm.getUserData = getUserData;

});