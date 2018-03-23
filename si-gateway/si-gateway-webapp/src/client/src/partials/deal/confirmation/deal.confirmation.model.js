"use strict";
app.factory('dealConfirmationModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('api/deal/confirmation');
    });


    function initialize(link) {
        return rest.one("/initialize?link=" + link).customGET();
    }

    function addUser(link) {
        return rest.one('/add?link=' + link).customGET();
    }

    function removeUser(link) {
        return rest.one('/remove?link=' + link).customGET();
    }


    return {
        removeUser: removeUser,
        addUser: addUser,
        initialize: initialize
    };
});