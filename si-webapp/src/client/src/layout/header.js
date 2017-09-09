app.controller("headerController", function ($window, $rootScope, languageService, sessionService, constantService, userInitService, $filter, $sessionStorage, $state, $timeout) {

    var vm = this;

    vm.sessionService = sessionService;
    $timeout(function () {
        $rootScope.brand = ($filter('translate')("Header.brand"));
    }, 0);
    vm.initialization = {};

    function init() {
        constantService.init().then(function () {

            vm.initialization.languages = $sessionStorage.languages;
        });
        if (vm.sessionService.isAuthenticated()) {
            userInitService.init();
        }
    }


    function setLanguageCode(code, id) {
        languageService.updateUserLanguage({"id": id, "description": code});
        $window.location.reload();
    }

    init();

    vm.setLanguageCode = setLanguageCode;

});