/**
 * Created by evgeny on 7/10/2017.
 */
app.factory('registrationConfirmationModel', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/users/registration/confirmation');
    });

    function confirmRegistration(urlLink) {
        return rest.one('/' + urlLink).customGET();
    }

    return {
        confirmRegistration: confirmRegistration
    }
});