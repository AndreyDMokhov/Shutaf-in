app.controller("headerController", function (languageService, sessionService, constantService) {

    var vm = this;

    vm.sessionService = sessionService;
    vm.initialization = {};

    function init() {
        constantService.init();
        vm.initialization.languages = constantService.getLanguages();
    }


    function setLanguageCode(code, id) {
        languageService.setLanguage(code);
        languageService.updateUserLanguage({"languageId" : id});
    }

    init();

    vm.setLanguageCode = setLanguageCode;

});