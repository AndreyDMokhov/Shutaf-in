app.factory('constantService', function (Restangular, $q, $sessionStorage) {

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
        data = rest.one("all").withHttpConfig({timeout: 10000});
        data.get().then(
            function success(success) {
                data.languages = success.languages;
                $sessionStorage.languages = data.languages;

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