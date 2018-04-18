"use strict";
app.controller('logoutController', function (
                                            $rootScope,
                                            logoutModel,
                                            $state,
                                            languageService,
                                            $sessionStorage,
                                            browserTitleService) {

    browserTitleService.setExplicitTitle('');

    function logout() {
        logoutModel.logout().then(function (success) {
        });
        setTimeout(function () {
            $state.go('home');
        });
        delete $sessionStorage.sessionId;
        languageService.setDefaultLanguage();  //set default GUI language
        delete $sessionStorage.userProfile;
        delete $sessionStorage.questions;
        delete $sessionStorage.selectedAnswers;
        delete $sessionStorage.cities;
        delete $sessionStorage.countries;
        delete $sessionStorage.genders;
        delete $sessionStorage.questionsExtended;
        delete $sessionStorage.questionImportance;
        delete $sessionStorage.selectedExtendedAnswers;
        delete $sessionStorage.showExtendedQuestions;
        delete $sessionStorage.accountStatus;
        angular.forEach($sessionStorage.userIdsInChat, function (userId) {
           delete $sessionStorage[userId];
        });
        delete $sessionStorage.userIdsInChat;
    }

    logout();
});
