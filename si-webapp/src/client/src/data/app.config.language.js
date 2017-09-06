app.factory('languageService', function ($translate, Restangular, $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/users/settings');
    });

    function getUserLanguage() {
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        return rest.one('/language').customGET();
    }

    function updateUserLanguage(params) {
        _setLanguage(params.description);
        $sessionStorage.currentLanguage = params;

        var sessionId = $sessionStorage.sessionId;
        if (sessionId === undefined || sessionId === null) {

            return;
        }

        rest.setDefaultHeaders({"session_id": sessionId});

        if ($sessionStorage.currentLanguage !== params.id) {
            return rest.one('/language').customPUT({id: params.id});
        }
    }

    function _setLanguage(code) {
        if (code === undefined || code === null) {
            setDefaultLanguage();
            return;
        }
        $translate.use(code);
    }

    function setDefaultLanguage() {
        var defaultLanguageCode = 'en';
        delete $sessionStorage.currentLanguage;
        $sessionStorage.currentLanguage = {id: 1, description: "en"}
        $translate.use(defaultLanguageCode);
    }


    return {
        setDefaultLanguage: setDefaultLanguage,
        updateUserLanguage: updateUserLanguage,
        getUserLanguage: getUserLanguage
    }
});