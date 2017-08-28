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
        .state("settings",
            {
                templateUrl: "partials/settings/settings.html",
                controller: "settingsController",
                controllerAs: "vm",
                url: "/settings"
            })
        .state("userProfile",
            {
                templateUrl: "partials/userProfile/userProfile.html",
                controller: "userProfileController",
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
        .state("changeEmailRequest",
            {
                templateUrl: "partials/changeEmail/changeEmail.html",
                controller: "changeEmailRequestController",
                controllerAs: "vm",
                url: "/settings/change-email/request"
            })
        .state("changeEmailConfirmation",
            {
                controller: "changeEmailConfirmationController",
                controllerAs: "vm",
                url: "/settings/change-email/confirmation/{link}"
            })
        .state("changePassword",
            {
                templateUrl: 'partials/changePassword/changePassword.html',
                controller: "changePasswordController",
                controllerAs: "vm",
                url: "/settings/change-password"
            })
        .state("resetPasswordRequest",
            {
                templateUrl: "partials/resetPassword/resetPassword.request.html",
                controller: "resetPasswordRequestController",
                controllerAs: "vm",
                url: "/reset-password/request"
            })
        .state('resetPasswordNewPassword',
            {
                url: "/reset-password/confirmation/{link}",
                templateUrl: 'partials/resetPassword/resetPassword.confirmation.html',
                controller: 'resetPasswordConfirmation',
                controllerAs: "vm"
            })
        .state("chat",
            {
                templateUrl:'partials/chat/chat.html',
                controller: "chatController",
                controllerAs: "vm",
                url: "/chat"
            })
    ;
    $urlRouterProvider.otherwise("/home");
});