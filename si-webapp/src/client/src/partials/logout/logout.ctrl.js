"use strict";
app.controller('logoutController', function ($rootScope, logoutModel, $window, $state,$filter, languageService, $sessionStorage) {
    $window.document.title = "Shutaf-In | " +$state.current.title;
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
    $rootScope.brand = 'Shutaf-In';
    }

   logout();
});
