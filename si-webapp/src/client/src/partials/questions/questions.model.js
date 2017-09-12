app.factory('quizFactory', function (Restangular, $q, $sessionStorage, notify) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/user/match');
        RestangularProvider.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
    });

    function getQuestions() {
        return rest.one('/template').customGET();
    }

    function sendAnswers(params) {
        return rest.one('/save').customPUT(params);
    }

    return {
        getQuestions: getQuestions,
        sendAnswers: sendAnswers
    };
});