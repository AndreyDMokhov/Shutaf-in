app.controller('logoutController', function (logoutModel, $window, $state, languageService) {

    function logout() {
        logoutModel.logout().then(function (success) {
        });
        setTimeout(function(){
            $state.go('home');
        });
        localStorage.removeItem('session_id');
        languageService.setLanguage(null);  //set default GUI language
    }

   logout();
});
