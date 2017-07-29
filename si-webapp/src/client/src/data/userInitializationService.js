app.factory('userInitService', function (Restangular, $q, $rootScope, $sessionStorage) {
    var rest = Restangular.withConfig(function (Configurer) {
        Configurer.setBaseUrl('/api/userInitialization');
    });

    var data = {};

    function getUserProfile() {
        return data.userProfile || $sessionStorage.userProfile || rest.all('userProfile').get().$object;
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
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                if (error.data.error.errorTypeCode === 'AUT') {
                    $state.go('logout');
                }
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