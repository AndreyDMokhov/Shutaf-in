"use strict";
app.factory('quizModel', function (Restangular, $sessionStorage) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
    });

    function sendAnswers(params) {
        return rest.one('/api/users/match/save').customPOST(params);
    }

    return {
        sendAnswers: sendAnswers
    };
});