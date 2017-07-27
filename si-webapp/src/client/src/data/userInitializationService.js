app.factory('userInitService', function (Restangular, $q, $rootScope, $sessionStorage) {
    var rest = Restangular.withConfig(function (Configurer) {
        Configurer.setBaseUrl('/api/userInitialization');
    });

    var data = {};

    function getUserProfile() {
        data.userProfile = data.userProfile || $sessionStorage.userProfile || rest.all('userProfile').get().$object;
        //relevant?
        console.log(data.userProfile);
        return data.userProfile;
    }

    function init() {

        var deferred = $q.defer();
        rest.setDefaultHeaders({'session_id':$sessionStorage.sessionId});
        data = rest.one("init").withHttpConfig({timeout: 10000});
        data.get().then(
            function (success) {
                data.userProfile = success.userProfile;
                $sessionStorage.userProfile = data.userProfile;
                $rootScope.brand = success.userProfile.firstName +" " + success.userProfile.lastName;


                deferred.resolve(data);
            },
            function (error) {
                return deferred.reject();
            }
        );

        return deferred.promise;
    }

    return {
        init: init,
        getUserProfile: getUserProfile
    }
});