"use strict";
app.factory('myUserProfileModel', function (Restangular, $sessionStorage) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    function addOrUpdateImage(params) {
        return rest.one('/api/users/settings/image').customPOST(params);
    }

    function deleteImage() {
        return rest.one('/api/users/settings/image').customDELETE();
    }


    return {
        addOrUpdateImage: addOrUpdateImage,
        deleteImage: deleteImage
    };
});