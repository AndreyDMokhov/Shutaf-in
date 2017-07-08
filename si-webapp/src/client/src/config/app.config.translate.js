app.config(function ($translateProvider, CACHED_LANGUAGE, CACHED_LANGUAGE_ID) {

    $translateProvider.useStaticFilesLoader({
        prefix: 'resources/dictionaries/lang-',
        suffix: '.json'
    });

    function _defineLanguage() {
        var currentLanguage = localStorage.getItem(CACHED_LANGUAGE);
        if (currentLanguage === undefined || currentLanguage === null) {
            localStorage.setItem(CACHED_LANGUAGE, 'en');
            localStorage.setItem(CACHED_LANGUAGE_ID, '1');
            currentLanguage = 'en';
        }

        return currentLanguage;
    }

    $translateProvider.preferredLanguage(_defineLanguage());
    $translateProvider.useMissingTranslationHandlerLog();

    $translateProvider.useSanitizeValueStrategy('escape');
});