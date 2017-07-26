app.factory('userProfileModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/users/settings');
    });

    function addImage(params) {
        rest.setDefaultHeaders({'session_id': localStorage.getItem('session_id')});
        return rest.one('/image').customPUT(params)
    }

    function deleteImage() {
        rest.setDefaultHeaders({'session_id': localStorage.getItem('session_id')});
        return rest.one('/image').customDELETE()
    }


    return {
        addImage: addImage,
        deleteImage: deleteImage
    }
})
