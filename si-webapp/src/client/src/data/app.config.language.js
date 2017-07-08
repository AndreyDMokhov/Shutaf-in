app.factory('languageService', function ($translate, CACHED_LANGUAGE, CACHED_LANGUAGE_ID, Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/user/account');
    });

    function getUserLanguage() {
        rest.setDefaultHeaders({"session_id" : localStorage.getItem("session_id")});
        return rest.one('/language').customGET();
    }

    function updateUserLanguage(params) {
        localStorage.setItem(CACHED_LANGUAGE_ID, params.id);
        localStorage.setItem(CACHED_LANGUAGE, params.description);

        _setLanguage(params.description);


        var sessionId = localStorage.getItem("session_id");
        if (sessionId === undefined || sessionId === null){
            return;
        }
        rest.setDefaultHeaders({"session_id" : sessionId});
        return rest.one('/language').customPUT(params.id);
    }

    function _setLanguage(code) {
        if (code === undefined || code === null) {
            localStorage.setItem(CACHED_LANGUAGE, 'en');
            $translate.use(localStorage.getItem(CACHED_LANGUAGE));
            return;
        }

        $translate.use(code);
    }

    function setDefaultLanguage() {
        var defaultLanguageCode = 'en';
        localStorage.setItem(CACHED_LANGUAGE, defaultLanguageCode);
        localStorage.removeItem(CACHED_LANGUAGE_ID);
        $translate.use(defaultLanguageCode);
    }


    return {
        setDefaultLanguage:setDefaultLanguage,
        updateUserLanguage:updateUserLanguage,
        getUserLanguage:getUserLanguage
    }
});