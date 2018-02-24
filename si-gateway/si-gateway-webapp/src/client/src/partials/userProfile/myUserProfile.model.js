"use strict";
app.factory('myUserProfileModel', function (Restangular, $sessionStorage) {
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

    function getSelectedUserProfile(userId) {
        return rest.one('/api/users/my-profile/'+userId).customGET();
    }

    return {
        addOrUpdateImage: addOrUpdateImage,
        deleteImage: deleteImage,
        getSelectedUserProfile: getSelectedUserProfile
    };
});