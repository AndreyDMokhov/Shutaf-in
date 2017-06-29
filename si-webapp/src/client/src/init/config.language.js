app.factory('languageService', function ($translate, CACHED_LANGUAGE, $state, Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api');
        RestangularProvider.setDefaultHeaders({"session_id" : localStorage.getItem("session_id")});
    });

    function getUserLanguage() {
        return rest.one('/user/account/get').customGET();
    }

    function updateUserLanguage(params) {
        return rest.one('/user/account/update').customPUT(params);
    }

    function setLanguage(code) {
        if (code === undefined || code === null) {
            localStorage.setItem(CACHED_LANGUAGE, 'en');
            $translate.use(code);
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