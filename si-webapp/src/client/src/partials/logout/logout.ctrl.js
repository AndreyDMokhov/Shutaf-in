app.controller('logoutController', function ($rootScope, logoutModel, $window, $state,$filter) {

    function logout() {
        logoutModel.logout().then(function (success) {
        });
        setTimeout(function(){
            $state.go('home');
        });
        localStorage.removeItem('session_id');
        sessionStorage.removeItem('userProfile');
    $rootScope.brand = ($filter('translate')('Header.brand'));
    }

   logout();
});
