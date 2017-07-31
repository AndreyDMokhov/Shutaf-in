app.factory('constantService', function (Restangular, $q, $sessionStorage, $localStorage) {

    var rest = Restangular.withConfig(function (Configurer) {
        Configurer.setBaseUrl('/api/initialization');
    });

    var data = [];

    function getLanguages() {
        data.languages = data.languages || $sessionStorage.languages || rest.all('languages').getList().$object;
        return data.languages;
    }

    function init() {
        var deferred = $q.defer();
        data = rest.one("all/" + $sessionStorage.currentLanguage.id).withHttpConfig({timeout: 10000});
        data.get().then(
            function success(success) {
                data.languages = success.languages;
                $sessionStorage.languages = success.languages;
                $localStorage.cities = success.cities;
                $localStorage.countries = success.countries;
                $localStorage.genders = success.genders;

                deferred.resolve(data);
            },
            function error() {
                return deferred.reject();
            }
        );
        return deferred.promise;
    }

    return {
        init:init,
        getLanguages:getLanguages
    }
});