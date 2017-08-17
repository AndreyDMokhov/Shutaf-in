app.controller("headerController", function ($state, $rootScope, languageService, sessionService, constantService, userInitService, $filter) {

    var vm = this;

    vm.sessionService = sessionService;
    $rootScope.brand = ($filter('translate')("Header.brand"));
    vm.initialization = {};

    function init() {
        vm.initialization.languages = constantService.getLanguages();
        if (vm.sessionService.isAuthenticated()) {
            userInitService.init();
            constantService.init();
        }
    }


    function setLanguageCode(code, id) {
        languageService.updateUserLanguage({"id": id, "description": code});
        constantService.init();
    }

    init();

    vm.setLanguageCode = setLanguageCode;

});