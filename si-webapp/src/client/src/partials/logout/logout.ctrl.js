"use strict";
app.controller('logoutController', function ($rootScope, logoutModel, $window, $state,$filter, languageService, $sessionStorage) {

    function logout() {
        logoutModel.logout().then(function (success) {
        });
        setTimeout(function(){
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
    $rootScope.brand = 'Shutaf-In';
    }

   logout();
});
