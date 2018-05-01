"use strict";
app.controller('logoutController', function (
                                            $rootScope,
                                            logoutModel,
                                            $state,
                                            languageService,
                                            $sessionStorage,
                                            browserTitleService,
                                            sessionStorageObserver,
                                            authenticationService) {

    browserTitleService.setExplicitTitle('');

    function logout() {
        logoutModel.logout().then(function (success) {
        });
        setTimeout(function () {
            $state.go('home');
        });
        authenticationService.removeSessionId();

        languageService.setDefaultLanguage();  //set default GUI language
        delete $sessionStorage.userProfile;
        delete $sessionStorage.selectedAnswers;
        delete $sessionStorage.selectedExtendedAnswers;
        delete $sessionStorage.showExtendedQuestions;
        delete $sessionStorage.accountStatus;
        angular.forEach($sessionStorage.userIdsInChat, function (userId) {
           delete $sessionStorage[userId];
        });
        delete $sessionStorage.userIdsInChat;
        sessionStorageObserver.notifyServiceObservers();
    }

    logout();
});
