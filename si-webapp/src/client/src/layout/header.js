app.controller("headerController", function (languageService, sessionService) {

    var vm = this;

    vm.sessionService = sessionService;

    function setLanguageCode(code) {
        languageService.setLanguage(code);
    }

    vm.setLanguageCode = setLanguageCode;

});