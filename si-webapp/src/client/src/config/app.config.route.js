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
                url: "/registration/request"
            })
        .state("registrationConfirmation",
            {
                controller: "registrationConfirmation",
                controllerAs: "vm",
                url: "/users/registration/confirmation/{link}"
            })
        .state("error",
            {
                templateUrl: "partials/errors/errors.html",
                controller: "errorsController",
                controllerAs: "vm",
                url: "/error/{code}"
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
        .state("security-settingsPassword",
            {
                templateUrl: "partials/securitySettingsPassword/securitySettingsPassword.html",
                controller: "securitySettingsPasswordController",
                controllerAs: "vm",
                url: "/account/security-settings/password"
            });
    $urlRouterProvider.otherwise("/home");
});