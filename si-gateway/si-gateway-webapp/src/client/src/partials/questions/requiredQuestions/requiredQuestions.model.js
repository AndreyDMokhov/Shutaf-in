"use strict";
app.factory('quizModel', function (Restangular, $sessionStorage) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    function sendAnswers(params) {
        return rest.one('/api/users/matching/required').customPOST(params);
    }

    function getRequiredAnswers(userId){
        return rest.one('/api/users/matching/required-answers/'+userId).customGET();
    }

    return {
        sendAnswers: sendAnswers,
        getRequiredAnswers:getRequiredAnswers
    };
});