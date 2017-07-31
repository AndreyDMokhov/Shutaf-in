app.factory('languageService', function ($translate, Restangular, $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/user/account');
    });

    function getUserLanguage() {
        rest.setDefaultHeaders({'session_id':$sessionStorage.sessionId});
        return rest.one('/language').customGET();
    }

    function updateUserLanguage(params) {
        $sessionStorage.currentLanguage = params;
        _setLanguage(params.description);


        var sessionId = $sessionStorage.sessionId;
        if (sessionId === undefined || sessionId === null){

            return;
        }
        rest.setDefaultHeaders({"session_id" : sessionId});
        return rest.one('/language').customPUT(params.id);
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
        $sessionStorage.currentLanguage={id:1, decription:"en"}
        $translate.use(defaultLanguageCode);
    }


    return {
        setDefaultLanguage:setDefaultLanguage,
        updateUserLanguage:updateUserLanguage,
        getUserLanguage:getUserLanguage
    }
});