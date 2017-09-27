"use strict";
app.config(function ($translateProvider, $sessionStorageProvider) {

    $translateProvider.useStaticFilesLoader({
        prefix: 'resources/dictionaries/lang-',
        suffix: '.json'
    });

    function _defineLanguage() {
        var languages = $sessionStorageProvider.get('languages');
        var currentLanguageId;
        var currentLanguage = null;

        if (languages) {
            languages.forEach(function (language) {
                currentLanguageId = $sessionStorageProvider.get('currentLanguage');

                if (currentLanguageId === language.id) {
                    currentLanguage = language;
                }
            });
        }

        if (!currentLanguage) {

            currentLanguage = {id:1, description:'en'};
            $sessionStorageProvider.set('currentLanguage', currentLanguage);
        }

        return currentLanguage.description;
    }


    $translateProvider.preferredLanguage(_defineLanguage());
    $translateProvider.useMissingTranslationHandlerLog();

    $translateProvider.useSanitizeValueStrategy('escape');
});