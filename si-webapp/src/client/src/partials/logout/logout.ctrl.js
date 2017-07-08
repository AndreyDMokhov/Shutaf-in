app.controller('logoutController', function ($rootScope, logoutModel, $window, $state,$filter, languageService) {

    function logout() {
        logoutModel.logout().then(function (success) {
        });
        setTimeout(function(){
            $state.go('home');
        });
        localStorage.removeItem('session_id');
        languageService.setLanguage(null);  //set default GUI language
        sessionStorage.removeItem('userProfile');
    $rootScope.brand = ($filter('translate')('Header.brand'));
    }

   logout();
});
