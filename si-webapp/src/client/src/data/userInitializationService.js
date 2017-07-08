app.factory('userInitService', function (Restangular, $q, $rootScope) {
    var rest = Restangular.withConfig(function (Configurer) {
        Configurer.setBaseUrl('/api/userInitialization');
    });

    var data = {};

    function getUserProfile() {
        data.userProfile = data.userProfile || JSON.parse(sessionStorage.getItem("userProfile")) || rest.all('userProfile').get().$object;
        console.log(data.userProfile);
        return data.userProfile;
    }

    function init() {

        var deferred = $q.defer();
        rest.setDefaultHeaders({'session_id':localStorage.getItem('session_id')});
        data = rest.one("init").withHttpConfig({timeout: 10000});
        data.get().then(
            function (success) {
                data.userProfile = success.userProfile;
                sessionStorage.setItem("userProfile", JSON.stringify(data.userProfile));
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