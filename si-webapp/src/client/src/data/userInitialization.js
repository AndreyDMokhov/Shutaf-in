app.factory('userInitService', function (Restangular, $q) {
    var rest = Restangular.withConfig(function (Configurer) {
        Configurer.setBaseUrl('/api/userInitialization');
    });

    var data = [];
    var sessionId = localStorage.getItem('session_id');

    function getUserProfile() {
        data.userProfile = data.userProfile || JSON.parse(sessionStorage.getItem("userProfile")) || rest.all('userProfile').getList().$object;
        return data.userProfile;
    }

    function init(sessionId) {
        var deferred = $q.defer();
        data = rest.one("init").withHttpConfig({timeout: 10000});
        data.customPOST(sessionId).then(
            function success(success) {
                data.userProfile = success.userProfile;
                sessionStorage.setItem("userProfile", JSON.stringify(data.userProfile));
                deferred.resolve(data);
            },
            function error() {
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