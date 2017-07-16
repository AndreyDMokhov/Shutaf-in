app.config(function ($stateProvider, $urlRouterProvider) {
    $stateProvider
        .state("home",
            {
                templateUrl: "partials/home/home.html",
                controller: "homeController",
                controllerAs: "vm",
                url: "/home"
            })
        .state("about",
            {
                templateUrl: "partials/about/about.html",
                controller: "aboutController",
                controllerAs: "vm",
                url: "/about"
            })

        .state("registration",
            {
                templateUrl: "partials/registration/registration.html",
                controller: "userRegistration",
                controllerAs: "vm",
                url: "/registration"
            })
        .state("users",
            {
                templateUrl: "partials/users/users.html",
                controller: "usersController",
                controllerAs: "vm",
                url: "/users"
            })
        .state("userProfile",
            {
                templateUrl: "partials/userProfilePage/userProfilePage.html",
                controller: "userProfilePage",
                controllerAs: "vm",
                url: "/userProfile"
            })

        .state("login",
            {
                templateUrl: "partials/login/login.html",
                controller: "loginController",
                controllerAs: "vm",
                url: "/login"
            })
        .state("logout",
            {
                controller: "logoutController",
                url: "/logout"
            })
        .state("resetPassword",
            {
                templateUrl: "partials/resetPassword/resetPasswordInputEmail.html",
                controller: "resetPasswordController",
                controllerAs: "vm",
                url: "/resetPassword"
            })
        .state("error404",
            {
                templateUrl: 'partials/resetPassword/page404.html',
                controller: 'error404Controller',
                controllerAs: "vm"
            })
        .state('inputNewPassword', {
            url: "/reset-password/confirmation/{link}",
            templateUrl: 'partials/resetPassword/resetPasswordInputNewPassword.html',
            controller: 'resetPasswordConfirmation',
            controllerAs: "vm"
        });

    $urlRouterProvider.otherwise("/home");
});