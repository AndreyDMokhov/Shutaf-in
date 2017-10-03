"use strict";
app.controller("headerController", function (
                                            $rootScope,
                                            languageService,
                                            sessionService,
                                            $filter,
                                            $sessionStorage,
                                            $state,
                                            $window,
                                            initializationService,
                                            webSocketService) {

    var vm = this;
    vm.userProfile = {};

    vm.sessionService = sessionService;
    $rootScope.brand = "Shutaf-In";
    vm.initialization = {};
    function init() {
        initializationService.initializeLanguages().then(function (languages) {
            vm.initialization.languages = languages;
            languageService.setFrontendLanguage($sessionStorage.currentLanguage);
            if ($sessionStorage.sessionId) {
                initializationService.initializeApplication().then(function () {
                    vm.userProfile = $sessionStorage.userProfile;
                    $rootScope.brand = vm.userProfile.firstName + ' ' + vm.userProfile.lastName;
                    webSocketService.getConnection();
                });
            }
        });
    }


    function setLanguageCode(languageId) {
        languageService.updateUserLanguage(languageId);
        $state.go('home', {}, {reload:true});
        $window.location.reload();
    }

    init();

    vm.setLanguageCode = setLanguageCode;

});