app.config(function ($translateProvider, $sessionStorageProvider) {

    $translateProvider.useStaticFilesLoader({
        prefix: 'resources/dictionaries/lang-',
        suffix: '.json'
    });

    function _defineLanguage() {
        var currentLanguage = $sessionStorageProvider.get('currentLanguage');

        if (_isUndefinedOrNull(currentLanguage)) {

            var currentLanguage = {"id":1, "description":"en"};
            $sessionStorageProvider.set('currentLanguage', currentLanguage);
        }

        return currentLanguage.description;
    }

    function _isUndefinedOrNull(value) {
        return value === undefined || value === null;
    }

    $translateProvider.preferredLanguage(_defineLanguage());
    $translateProvider.useMissingTranslationHandlerLog();

    $translateProvider.useSanitizeValueStrategy('escape');
});