app.factory('initializationService', function (Restangular, $q, $sessionStorage, notify, $filter, languageService) {

    var rest = Restangular.withConfig(function (RestangularProvider) {

    });

    function initializeLanguages() {
        var deferred = $q.defer();
        rest.one('/api/initialization/languages').customGET().then(
            function (success) {
                $sessionStorage.languages = success.data;

                deferred.resolve(success.data);
            }, function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                return deferred.reject();
            });
        return deferred.promise;
    }

    function initializeApplication() {
        var deferred = $q.defer();

        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        rest.one('/api/initialization/all').customGET().then(
            function (success) {

                $sessionStorage.userProfile = success.data.userProfile;
                $sessionStorage.cities = success.data.cities;
                $sessionStorage.countries = success.data.countries;
                $sessionStorage.genders = success.data.genders;
                $sessionStorage.questions = success.data.questionAnswersResponses;
                $sessionStorage.selectedAnswers = _getSelectedAnswers(success.data.selectedAnswersResponses);
                $sessionStorage.questionsExtended = success.data.questionExtendedWithAnswers;
                $sessionStorage.questionImportance = success.data.questionImportanceList;
                $sessionStorage.selectedExtendedAnswers = success.data.selectedExtendedAnswersResponses;

                languageService.setFrontendLanguage($sessionStorage.userProfile.languageId);

                deferred.resolve(success.data);
            }, function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                return deferred.reject();
            });
        return deferred.promise;
    }

    function _getSelectedAnswers(data) {
        var answers = [];
        for (var i = 0; i < $sessionStorage.questions.length; i++) {
            if (data[i]) {
                answers.push({"questionId": data[i].questionId, "answerId": data[i].selectedAnswersIds[0]});
            }
            else {
                answers.push({"questionId": i+1, "answerId": null});
            }
        }
        return answers;
    }

    return {
        initializeLanguages: initializeLanguages,
        initializeApplication: initializeApplication
    };
});