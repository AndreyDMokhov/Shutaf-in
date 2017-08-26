app.controller("headerController", function ($rootScope, languageService, sessionService, constantService, userInitService, $filter,$timeout, $sessionStorage) {

    var vm = this;

    vm.sessionService = sessionService;
    $timeout(function () {
        $rootScope.brand = ($filter('translate')("Header.brand"));
    }, 0);
    vm.initialization = {};

    function init() {
        constantService.init();
        vm.initialization.languages = $sessionStorage.languages;
        if (vm.sessionService.isAuthenticated()) {
            userInitService.init();

        }
    }
    function setLanguageCode(code, id) {
        languageService.updateUserLanguage({"id" : id, "description" : code});
        constantService.init();
    }

    init();

    vm.setLanguageCode = setLanguageCode;

});