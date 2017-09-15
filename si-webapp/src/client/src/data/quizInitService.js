app.factory('quizInitService', function (Restangular, $q, $rootScope, $sessionStorage, notify, $filter) {
    var rest = Restangular.withConfig(function (Configurer) {

    });


    function init() {

        var deferred = $q.defer();
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        var questions = rest.one("/api/users/match/questionnaire/initialization").withHttpConfig({timeout: 10000});
        _getDataFromResponse(questions).then(
            function (success) {
                $sessionStorage.questions = success.data;
                var answers = rest.one("/api/users/match/questionnaire/answers").withHttpConfig({timeout: 10000});
                _getDataFromResponse(answers).then(
                    function (success) {
                        _fillAnswerArray(success.data);
                    }
                );
            }
        );
        return deferred.promise;
    }

    function _getDataFromResponse(data) {
        var deferred = $q.defer();
        data.get().then(
            function (success) {
                deferred.resolve(success);
            },
            function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                if (error.data.error.errorTypeCode === 'AUT') {
                    $state.go('logout');
                }
                return deferred.reject();
            });
        return deferred.promise;
    }

    function _fillAnswerArray(data) {
        var answers = [];
        for (var i = 0; i < $sessionStorage.questions.length; i++) {
            if (data[i]) {
                answers.push({"questionId": i, "answerId": data[i].selectedAnswersIds[0]})
            }
            else {
                answers.push({"questionId": i, "answerId": null})
            }
            $sessionStorage.answers = answers;
        }
    }


    return {
        init: init
    }
});
