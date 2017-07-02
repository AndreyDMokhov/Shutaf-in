app.controller("headerController", function (languageService, sessionService, constantService) {

    var vm = this;

    vm.sessionService = sessionService;

    function init() {
        constantService.init();
    }


    function setLanguageCode(code) {
        languageService.setLanguage(code);
        languageService.updateUserLanguage({"languageId" : id});
    }


    init();

    vm.setLanguageCode = setLanguageCode;

});