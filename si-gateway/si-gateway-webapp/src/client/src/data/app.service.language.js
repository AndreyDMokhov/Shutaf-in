"use strict";
app.service('languageService', function (
                                        $translate,
                                        Restangular,
                                        $sessionStorage,
                                        authenticationService) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
    });

    /**
     * Returns language object, by languageId.
     * Refers to $sessionStorage.languages array
     * If languageId is undefined or null -> sets default language object {id:1, description:'en'}
     *
     * @param languageId
     * @returns {{description: string, id: number}}
     */
    function getLanguage(languageId) {
        var languages = $sessionStorage.languages;
        var defaultLanguage = {description: 'en', id: 1};
        var returnLanguage = defaultLanguage;

        if (!languages) {
            return defaultLanguage;
        }
        languages.forEach(function (language) {
            if (language.id === languageId) {
                returnLanguage = language;
            }
        });

        return returnLanguage;
    }

    /**
     * Same as getLanguage, but takes language id from $sessionStorage.userProfile
     */
    function getUserLanguage() {
        var userProfile = $sessionStorage.userProfile;
        return getLanguage(userProfile.languageId);
    }

    /**
     * 1. Resolves language object by languageId
     * 2. Sets $sessionStorage.currentLanguage
     * 3. Sets $translate service to translate the frontend
     *
     * @param languageId
     */
    function setFrontendLanguage(languageId) {
        var language = getLanguage(languageId);

        $sessionStorage.currentLanguage = language.id;
        $translate.use(language.description);
    }

    /**
     * 1. Sets frontend language
     * 2. Sends language update request if sessionId is present
     *
     * @param languageId
     */
    function updateUserLanguage(languageId) {
        setFrontendLanguage(languageId);

        if (!authenticationService.isAuthenticated()) {
            return;
        }
        rest.one('/api/users/settings/language').customPUT({id: languageId});
    }


    function setDefaultLanguage() {
        var defaultLanguageCode = 'en';
        delete $sessionStorage.currentLanguage;
        $sessionStorage.currentLanguage = 1;
        $translate.use(defaultLanguageCode);
    }

    return {
        getLanguage: getLanguage,
        getUserLanguage: getUserLanguage,
        setFrontendLanguage: setFrontendLanguage,
        updateUserLanguage: updateUserLanguage,
        setDefaultLanguage: setDefaultLanguage
    };
});