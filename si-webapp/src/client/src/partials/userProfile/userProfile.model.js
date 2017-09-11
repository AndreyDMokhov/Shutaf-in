"use strict";
app.factory('userProfileModel', function (Restangular, $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/users/settings');
    });

    function addOrUpdateImage(params) {
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        return rest.one('/image').customPOST(params)
    }

    function deleteImage() {
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        return rest.one('/image').customDELETE()
    }


    return {
        addOrUpdateImage: addOrUpdateImage,
        deleteImage: deleteImage
    }
});
