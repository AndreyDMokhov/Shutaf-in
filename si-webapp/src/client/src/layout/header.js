app.controller("headerController", function ($rootScope, languageService, sessionService, constantService, userInitService, $filter, $sessionStorage, $state, $timeout, $window, quizInitService) {

    var vm = this;
    vm.userProfile = {};

    vm.sessionService = sessionService;
    $rootScope.brand = "Shutaf-In";
    vm.initialization = {};

    function init() {
        if (vm.sessionService.isAuthenticated()) {
            userInitService.init().then(function () {
                constantService.init().then(function () {
                    vm.initialization.languages = $sessionStorage.languages;
                });
                quizInitService.init();
                vm.userProfile = $sessionStorage.userProfile;
            });
        } else {
            constantService.init().then(function () {

                vm.initialization.languages = $sessionStorage.languages;
            });

        }
    }


    function setLanguageCode(code, id) {
        languageService.updateUserLanguage({"id": id, "description": code});
        $window.location.reload();
    }

    init();

    vm.setLanguageCode = setLanguageCode;

});