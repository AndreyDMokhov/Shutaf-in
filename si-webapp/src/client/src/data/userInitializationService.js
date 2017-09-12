app.factory('userInitService', function (Restangular, $q, $rootScope, $sessionStorage, notify, $filter) {
    var rest = Restangular.withConfig(function (Configurer) {

    });

    var data = {};

    function init() {

        var deferred = $q.defer();
        rest.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
        data = rest.one("/api/users/settings/info").withHttpConfig({timeout: 10000});
        data.get().then(
            function (success) {
                data.userProfile = success.data;
                $sessionStorage.userProfile = success.data;
                $rootScope.brand = $sessionStorage.userProfile.firstName + " " + $sessionStorage.userProfile.lastName;


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
        init: init
    }
});