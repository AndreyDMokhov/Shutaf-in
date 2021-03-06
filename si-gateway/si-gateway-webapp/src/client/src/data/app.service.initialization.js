app.factory('initializationService', function (messengerChannelService,
                                               Restangular,
                                               $q,
                                               $sessionStorage,
                                               $filter,
                                               languageService,
                                               webSocketService,
                                               sessionStorageObserver,
                                               notificationService) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
    });

    function initializeLanguages() {
        var deferred = $q.defer();
        rest.one('/api/initialization/languages').customGET().then(
            function (success) {
                $sessionStorage.languages = success.data;

                deferred.resolve(success.data);
            }, function (error) {
                return deferred.reject();
            });
        return deferred.promise;
    }

    function initializeApplication() {
        var deferred = $q.defer();

        rest.one('/api/initialization/all').customGET().then(
            function (success) {

                initialize(success);

                notificationService.initWsSubscription();
                webSocketService.getConnection();
                messengerChannelService.initWsSubscription();
                deferred.resolve(success.data);
            }, function (error) {
                return deferred.reject();
            });
        return deferred.promise;
    }

    function initialize(success){
        $sessionStorage.userProfile = success.data.accountInitialization.userProfile;
        $sessionStorage.cities = success.data.accountInitialization.cities;
        $sessionStorage.countries = success.data.accountInitialization.countries;
        $sessionStorage.genders = success.data.accountInitialization.genders;
        $sessionStorage.accountStatus = success.data.accountInitialization.userProfile.accountStatus;
        $sessionStorage.questions = success.data.matchingInitializationResponse.questionAnswersResponses;
        $sessionStorage.selectedAnswers = _getSelectedAnswers(success.data.matchingInitializationResponse.selectedAnswersResponses);
        $sessionStorage.questionsExtended = success.data.matchingInitializationResponse.questionExtendedWithAnswers;
        $sessionStorage.questionImportance = success.data.matchingInitializationResponse.questionImportanceList;
        $sessionStorage.selectedExtendedAnswers = success.data.matchingInitializationResponse.selectedExtendedAnswersResponses;
        $sessionStorage.isMathingEnabled = success.data.matchingInitializationResponse.isMatchingEnabled;
        $sessionStorage.showExtendedQuestions = showExtendedQuestions();
        $sessionStorage.filters = success.data.accountInitialization.filters;
        messengerChannelService.listOfChats = success.data.listOfChats;

        $sessionStorage.dealStatuses = success.data.dealInitializationResponse.dealStatuses;
        $sessionStorage.dealUserPermissions = success.data.dealInitializationResponse.dealUserPermissions;
        $sessionStorage.dealUserStatuses = success.data.dealInitializationResponse.dealUserStatuses;
        $sessionStorage.documentTypes = success.data.dealInitializationResponse.documentTypes;
        $sessionStorage.permissionTypes = success.data.dealInitializationResponse.permissionTypes;

        languageService.setFrontendLanguage($sessionStorage.userProfile.languageId);

       sessionStorageObserver.notifyServiceObservers();
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
        return $sessionStorage.selectedAnswers[0].answerId !== null;
    }

    return {
        initializeLanguages: initializeLanguages,
        initializeApplication: initializeApplication,
        initialize: initialize
    };
});