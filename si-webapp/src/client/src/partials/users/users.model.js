app.factory('usersModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/api/users');
    });


    function getUsers(params) {
        return rest.all('/').customGETLIST('', params);
    }

    function saveUser(params) {
        return rest.one('/save').customPOST(params);
    }

    function updateUser(param) {
        return rest.one('/update').customPUT(param);
    }

    function deleteUser(id) {
        return rest.one('/delete/' + id).customDELETE();
    }

    return {
        getUsers: getUsers,
        saveUser: saveUser,
        updateUser: updateUser,
        deleteUser: deleteUser
    }
});