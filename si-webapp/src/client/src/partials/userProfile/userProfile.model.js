"use strict";
app.factory('userProfileModel', function (Restangular, $sessionStorage) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    function addOrUpdateImage(params) {
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        return rest.one('/api/users/settings/image').customPOST(params);
    }

    function deleteImage() {
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        return rest.one('/api/users/settings/image').customDELETE();
    }

    return {
        addOrUpdateImage: addOrUpdateImage,
        deleteImage: deleteImage
    };
});