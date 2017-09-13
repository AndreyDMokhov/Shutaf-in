app.controller("headerController", function (webSocketService, $rootScope, languageService, sessionService, constantService, userInitService, $filter, $sessionStorage) {

    var vm = this;

    vm.sessionService = sessionService;
    $rootScope.brand = ($filter('translate')("Header.brand"));
    vm.initialization = {};

    function init() {
        constantService.init();
        vm.initialization.languages = $sessionStorage.languages;
        if (vm.sessionService.isAuthenticated()) {
            userInitService.init();
            webSocketService.getConnection();
        }
    }



    function setLanguageCode(code, id) {
        languageService.updateUserLanguage({"id" : id, "description" : code});
        constantService.init();
    }

    init();

    vm.setLanguageCode = setLanguageCode;

});