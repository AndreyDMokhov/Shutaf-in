/**
 * Created by evgeny on 7/10/2017.
 */
app.factory('registrationConfirmationModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    function confirmRegistration(urlLink) {
        return rest.one('/api/users/registration/confirmation/' + urlLink).customGET();
    }

    return {
        confirmRegistration: confirmRegistration
    }
});