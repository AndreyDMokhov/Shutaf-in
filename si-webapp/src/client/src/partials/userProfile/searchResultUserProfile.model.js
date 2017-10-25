/**
 * Created by evgeny on 10/24/2017.
 */
"use strict";
app.factory('searchResultUserProfileModel', function (Restangular, $sessionStorage) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    function getSelectedUserProfile(userId) {
        return rest.one('/api/user/profile/'+userId).customGET();
    }

    return {
        getSelectedUserProfile: getSelectedUserProfile
    };
});
