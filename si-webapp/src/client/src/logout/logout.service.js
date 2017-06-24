/**
 * Created by Rogov on 24.06.2017.
 */
app.factory('logoutService', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/api/logout');
    });

    function logout(params) {
        rest.setDefaultHeaders({'session_id':localStorage.getItem('session_id')});
        return  rest.one('/').customPOST(params);
    }

    return {
        logout:logout
    }
});