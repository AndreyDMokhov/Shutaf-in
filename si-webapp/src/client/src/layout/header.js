app.controller("headerController", function (languageService, sessionService,logoutService) {

    var vm = this;

    vm.sessionService = sessionService;


    function setLanguageCode(code) {
        languageService.setLanguage(code);
    }

    function logout() {
        logoutService.logout().then(function (success) {
        });
        setTimeout(function(){
            $window.location.reload();
        });
        localStorage.removeItem('session_id');
    }

    vm.logout = logout;
    vm.setLanguageCode = setLanguageCode;

});