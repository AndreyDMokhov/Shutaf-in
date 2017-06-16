app.factory('languageService', function ($translate, CACHED_LANGUAGE, $state) {

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
        setLanguage:setLanguage
    }




});