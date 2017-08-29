app.controller('logoutController', function ($rootScope, logoutModel, $window, $state,$filter, languageService, $sessionStorage) {

    function logout() {
        logoutModel.logout().then(function (success) {
        });
        delete $sessionStorage.sessionId;
        languageService.setDefaultLanguage();  //set default GUI language
        delete $sessionStorage.userProfile;
        setTimeout(function(){
            $state.go('home');
        });

    $rootScope.brand = ($filter('translate')('Header.brand'));
    }

   logout();
});
