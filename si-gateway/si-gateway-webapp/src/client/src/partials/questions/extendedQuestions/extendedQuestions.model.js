app.factory('extendedQuestionsModel', function (Restangular, $sessionStorage) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
    });

    function sendAnswers(params) {
        return rest.one('/api/matching/extended/').customPOST(params);
    }

    return {
        sendAnswers: sendAnswers
    };
});