app.controller('logoutController', function ($rootScope, logoutModel, $window, $state,$filter, languageService, $sessionStorage) {

    function logout() {
        logoutModel.logout().then(function (success) {
        });
        setTimeout(function(){
            $state.go('home');
        });
        delete $sessionStorage.sessionId;
        languageService.setDefaultLanguage();  //set default GUI language
        delete $sessionStorage.userProfile;
    $rootScope.brand = ($filter('translate')('Header.brand'));
    }

   logout();
});
