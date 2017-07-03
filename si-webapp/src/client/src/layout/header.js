app.controller("headerController", function (languageService, sessionService, constantService, userInitService) {

    var vm = this;

    vm.sessionService = sessionService;

    function init() {
        constantService.init();
    }

    if (vm.sessionService.isAuthenticated()) {
        var session_id = localStorage.getItem('session_id');
        userInitService.init(session_id);
    }

    function setLanguageCode(code) {
        languageService.setLanguage(code);
    }


    init();

    vm.setLanguageCode = setLanguageCode;

});