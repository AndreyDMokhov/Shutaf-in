app.factory('quizFactory', function (Restangular, $q, $sessionStorage, notify) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/user/match');
        RestangularProvider.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
    });
    var data = {};

    function getQuestions() {
        var deferred = $q.defer();
        data = rest.one("/template").withHttpConfig({timeout: 10000});
        data.get().then(
            function (success) {
                data.questionAnswers = success.data.data;
                return deferred.resolve(data.questionAnswers);
            },
            function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                return deferred.reject();
            }
        );
        return deferred.promise;
    }

    function sendAnswers(params) {
        return rest.one('/save').customPUT(params);
    }

    return {
        getQuestions: getQuestions,
        sendAnswers: sendAnswers
    };
});