app.controller("headerController", function ($rootScope, languageService, sessionService, constantService, userInitService, $filter) {

    var vm = this;

    vm.sessionService = sessionService;
    $rootScope.brand = ($filter('translate')("Header.brand"));

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