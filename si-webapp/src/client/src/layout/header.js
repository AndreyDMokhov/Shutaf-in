app.controller("headerController", function (languageService, sessionService, initializationService) {

    var vm = this;
    vm.initialization = {};
    var language = {};
    vm.sessionService = sessionService;

    function activate() {
        initializationService.getAll().then(
            function (result) {
                vm.initialization = result;
            }, function (err) {
                console.log(err);
                return err;
            }
        );

        languageService.getUserLanguage().then(
            function(result){//success
                language = result.data.language;
            },
            function(err){//fail
                console.log(err);
                return err;
            }
        );
        languageService.setLanguage(language.description);
    }

    function setLanguageCode(code, id) {
        languageService.setLanguage(code);
        languageService.updateUserLanguage({"languageId" : id});
    }

    activate();

    vm.setLanguageCode = setLanguageCode;

});