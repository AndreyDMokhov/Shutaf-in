app.controller("headerController", function ($rootScope, languageService, sessionService, constantService, userInitService, $filter, $sessionStorage, $state, $timeout, $window) {

    var vm = this;
    vm.userProfile = {};

    vm.sessionService = sessionService;
    $rootScope.brand = "Shutaf-In";
    vm.initialization = {};

    function init() {
        constantService.init().then(function () {

            vm.initialization.languages = $sessionStorage.languages;
        });
        if (vm.sessionService.isAuthenticated()) {
            userInitService.init().then(function () {

                vm.userProfile = $sessionStorage.userProfile;
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