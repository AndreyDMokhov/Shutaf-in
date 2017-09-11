"use strict";
app.factory('constantService', function (Restangular, $q, $sessionStorage, $localStorage) {

    var rest = Restangular.withConfig(function (Configurer) {
        Configurer.setBaseUrl('/api/initialization');
    });

    var data = [];

    function init() {
        var deferred = $q.defer();
        data = rest.one("all/" + $sessionStorage.currentLanguage.id).withHttpConfig({timeout: 10000});
        data.get().then(
            function success(success) {
                data.languages = success.data.languages;
                $sessionStorage.languages = success.data.languages;
                $localStorage.cities = success.data.cities;
                $localStorage.countries = success.data.countries;
                $localStorage.genders = success.data.genders;

                deferred.resolve(data);
            },
            function error() {
                return deferred.reject();
            }
        );
        return deferred.promise;
    }

    return {
        init:init
    };
});