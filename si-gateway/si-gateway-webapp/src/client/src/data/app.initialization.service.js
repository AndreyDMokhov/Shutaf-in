app.factory('initializationService', function (messengerChannelService,Restangular, $q, $sessionStorage, notify, $filter, languageService, webSocketService) {

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

                $sessionStorage.userProfile = success.data.accountInitialization.userProfile;
                $sessionStorage.cities = success.data.accountInitialization.cities;
                $sessionStorage.countries = success.data.accountInitialization.countries;
                $sessionStorage.genders = success.data.accountInitialization.genders;
                $sessionStorage.questions = success.data.matchingInitializationResponse.questionAnswersResponses;
                $sessionStorage.selectedAnswers = _getSelectedAnswers(success.data.matchingInitializationResponse.selectedAnswersResponses);
                $sessionStorage.questionsExtended = success.data.matchingInitializationResponse.questionExtendedWithAnswers;
                $sessionStorage.questionImportance = success.data.matchingInitializationResponse.questionImportanceList;
                $sessionStorage.selectedExtendedAnswers = success.data.matchingInitializationResponse.selectedExtendedAnswersResponses;
                $sessionStorage.listOfChats = success.data.listOfChats;
                $sessionStorage.showExtendedQuestions = showExtendedQuestions();
                $sessionStorage.filters = success.data.accountInitialization.filters;

                languageService.setFrontendLanguage($sessionStorage.userProfile.languageId);

                webSocketService.getConnection();
                messengerChannelService.initWsConnection();
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
                answers.push({"questionId": i + 1, "answerId": null});
            }
        }
        return answers;
    }
    function showExtendedQuestions() {
        if( $sessionStorage.selectedAnswers[0].answerId === null){
            return false;
        }
        else{
            return true;
        }
    }

    return {
        initializeLanguages: initializeLanguages,
        initializeApplication: initializeApplication
    };
});