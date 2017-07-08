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
        localStorage.setItem(CACHED_LANGUAGE_ID, params.languageId);

        var sessionId = localStorage.getItem("session_id");
        if (sessionId === undefined || sessionId === null){
            return;
        }
        rest.setDefaultHeaders({"session_id" : sessionId});
        return rest.one('/language').customPUT(params);
    }

    function setLanguage(code) {
        if (code === undefined || code === null) {
            localStorage.setItem(CACHED_LANGUAGE, 'en');
            $translate.use(localStorage.getItem(CACHED_LANGUAGE));
            return;
        }
        localStorage.setItem(CACHED_LANGUAGE, code);
        $translate.use(code);

    }


    return {
        setLanguage:setLanguage,
        updateUserLanguage:updateUserLanguage,
        getUserLanguage:getUserLanguage
    }




});