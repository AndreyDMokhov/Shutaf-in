app.controller("headerController", function ($rootScope, languageService, sessionService, constantService, userInitService, $filter,$timeout) {

    var vm = this;

    vm.sessionService = sessionService;

    $timeout(function () {
        $rootScope.brand = ($filter('translate')("Header.brand"));
    }, 0);

    vm.initialization = {};

    function init() {
        vm.initialization.languages = constantService.getLanguages();
        if (vm.sessionService.isAuthenticated()) {
            userInitService.init();
            constantService.init();
        }
    }



    function setLanguageCode(code, id) {
        languageService.updateUserLanguage({"id" : id, "description" : code});
        constantService.init();
    }

    init();

    vm.setLanguageCode = setLanguageCode;

});