app.factory('userProfileModel', function (Restangular,$sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/users/settings');
        RestangularProvider.setDefaultHeaders({'session_id':$sessionStorage.sessionId});
    });

    function addImage(params) {
        return rest.one('/image').customPUT(params)
    }

    function deleteImage() {
        return rest.one('/image').customDELETE()
    }


    return {
        addImage: addImage,
        deleteImage: deleteImage
    }
})
