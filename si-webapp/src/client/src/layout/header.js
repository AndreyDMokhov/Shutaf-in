app.controller("headerController", function ($rootScope, languageService, sessionService, constantService, userInitService, $filter) {

    var vm = this;

    vm.sessionService = sessionService;
    $rootScope.brand = ($filter('translate')("Header.brand"));
    vm.initialization = {};

    function init() {
        constantService.init();
        vm.initialization.languages = constantService.getLanguages();
        if (vm.sessionService.isAuthenticated()) {
            userInitService.init();
        }
    }



    function setLanguageCode(code, id) {
        languageService.setLanguage(code);
        languageService.updateUserLanguage({"languageId" : id});
    }

    init();

    vm.setLanguageCode = setLanguageCode;

});