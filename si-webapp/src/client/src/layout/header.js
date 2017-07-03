app.controller("headerController", function ($rootScope, languageService, sessionService, constantService, userInitService) {

    var vm = this;

    vm.sessionService = sessionService;
    // vm.brand="Shutaf-In";
    $rootScope.brand = "Shutaf-In";

    function init() {
        constantService.init();
    }

    if (vm.sessionService.isAuthenticated()) {
        userInitService.init();
    }

    function setLanguageCode(code) {
        languageService.setLanguage(code);
    }


    init();

    vm.setLanguageCode = setLanguageCode;

});