app.controller('logoutController', function (logoutModel, $window, $state) {

    function logout() {
        logoutModel.logout().then(function (success) {
        });
        setTimeout(function(){
            $window.location.reload();
            $state.go('home');
        });
        localStorage.removeItem('session_id');
    }

   logout();
});
