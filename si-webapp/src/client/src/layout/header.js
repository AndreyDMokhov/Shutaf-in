app.controller("headerController", function (languageService) {

    var vm = this;

    function setLanguageCode(code) {
        languageService.setLanguage(code);
    }

    vm.setLanguageCode = setLanguageCode

});