app.factory("matchingModel", function (Restangular, $sessionStorage) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/users/matching');
    });

    function saveMatchingStatus(params) {
        return  rest.one('/configure?enabled=' + params).customPUT();
    }
    return {
        saveMatchingStatus: saveMatchingStatus
    };
});
