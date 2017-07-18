app.config(function ($translateProvider, CACHED_LANGUAGE, CACHED_LANGUAGE_ID) {

    $translateProvider.useStaticFilesLoader({
        prefix: 'resources/dictionaries/lang-',
        suffix: '.json'
    });

    function _defineLanguage() {
        var currentLanguage = localStorage.getItem(CACHED_LANGUAGE);
        var currentLanguageId = localStorage.getItem(CACHED_LANGUAGE_ID);

        if (_isUndefinedOrNull(currentLanguage) || _isUndefinedOrNull(currentLanguageId)) {
            localStorage.setItem(CACHED_LANGUAGE, 'en');
            localStorage.setItem(CACHED_LANGUAGE_ID, '1');
            currentLanguage = 'en';
        }

        return currentLanguage;
    }

    function _isUndefinedOrNull(value) {
        return value === undefined || value === null;
    }

    $translateProvider.preferredLanguage(_defineLanguage());
    $translateProvider.useMissingTranslationHandlerLog();

    $translateProvider.useSanitizeValueStrategy('escape');
});