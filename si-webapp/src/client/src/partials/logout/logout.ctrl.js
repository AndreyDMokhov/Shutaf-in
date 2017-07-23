app.controller('logoutController', function ($rootScope, logoutModel, $window, $state,$filter, languageService, CACHED_USER_IMAGE_ID) {

    function logout() {
        logoutModel.logout().then(function (success) {
        });
        setTimeout(function(){
            $state.go('home');
        });
        localStorage.removeItem('session_id');
        localStorage.removeItem(CACHED_USER_IMAGE_ID);
        languageService.setDefaultLanguage();  //set default GUI language
        sessionStorage.removeItem('userProfile');
    $rootScope.brand = ($filter('translate')('Header.brand'));
    }

   logout();
});
