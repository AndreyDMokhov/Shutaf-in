app.factory('quizFactory', function (Restangular, $q, $sessionStorage, notify) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/users/match');
        RestangularProvider.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
    });

    function getQuestions() {
        return rest.one('/questionnaire/initialization').customGET();
    }

    function getAnswers() {
        return rest.one('/questionnaire/answers').customGET();
    }

    function sendAnswers(params) {
        return rest.one('/save').customPUT(params);
    }

    return {
        getQuestions: getQuestions,
        sendAnswers: sendAnswers,
        getAnswers:getAnswers
    };
});