"use strict";
app.factory('userProfileModel', function (Restangular, $sessionStorage) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    function getSelectedUserProfile(userId) {
        return rest.one('/api/users/profile/'+userId).customGET();
    }

    return {
        getSelectedUserProfile: getSelectedUserProfile
    };
});