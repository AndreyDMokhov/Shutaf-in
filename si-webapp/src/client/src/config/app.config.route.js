'use strict';
app.config(function ($stateProvider, $urlRouterProvider) {
    $stateProvider
        .state('home',
            {
                templateUrl: 'partials/home/home.html',
                controller: 'homeController',
                controllerAs: 'vm',
                url: '/home',
                title: 'Home'

            })
        .state('about',
            {
                templateUrl: 'partials/about/about.html',
                controller: 'aboutController',
                controllerAs: 'vm',
                url: '/about',
                title: 'About'
            })

        .state('registration',
            {
                templateUrl: 'partials/registration/registration.html',
                controller: 'userRegistration',
                controllerAs: 'vm',
                url: '/registration/request',
                title: 'Registration'
            })
        .state('registrationConfirmation',
            {
                controller: 'registrationConfirmation',
                controllerAs: 'vm',
                url: '/users/registration/confirmation/{link}'
            })
        .state('error',
            {
                templateUrl: 'partials/errors/errors.html',
                controller: 'errorsController',
                controllerAs: 'vm',
                url: '/error/{code}'
            })
        .state('users',
            {
                templateUrl: 'partials/users/users.html',
                controller: 'usersController',
                controllerAs: 'vm',
                url: '/users',
                title: 'Users'
            })
        .state('userProfile',
            {
                templateUrl: 'partials/userProfile/userProfile.html',
                controller: 'userProfileController',
                controllerAs: 'vm',
                url: '/profile/{id:int}',
            title: 'Profile'
            })

        .state('login',
            {
                templateUrl: 'partials/login/login.html',
                controller: 'loginController',
                controllerAs: 'vm',
                url: '/login',
                title: 'Login'
            })
        .state('logout', {
                controller: 'logoutController',
                url: '/logout',
            title: 'Logout'
        })
        .state('resetPasswordRequest', {
                templateUrl: 'partials/resetPassword/resetPassword.request.html',
                controller: 'resetPasswordRequestController',
                controllerAs: 'vm',
                url: '/reset-password/request',
            title: 'Reset password'
        })
        .state('resetPasswordNewPassword', {
                url: '/reset-password/confirmation/{link}',
                templateUrl: 'partials/resetPassword/resetPassword.confirmation.html',
                controller: 'resetPasswordConfirmation',
                controllerAs: 'vm',
            title: 'Reset password'
        })
        .state('userSearch', {
                templateUrl: 'partials/userSearch/userSearch.html',
                controller: 'userSearchController',
                controllerAs: 'vm',
                url: '/users/search?{name}',
            title: 'User search'
        })
        .state('questions', {
                templateUrl:'partials/questions/questions.html',
                controller: 'questionsCtrl',
                controllerAs: 'vm',
                url: '/questions',
            title: 'Questions'
            })
        /* Settings */
        .state('settings', {
            abstract: true,
            url: '/settings',
            controller: 'settingsController',
            controllerAs: 'vm',
            templateUrl: 'partials/settings/settings.html'
        })
        .state('settings.personal', {
            url:'/personal',
            templateUrl: 'partials/settings/userSettings/userSettings.html',
            controller: 'userSettingsController',
            controllerAs: 'vm',
            title: 'Settings'
        })
        .state('settings.changeEmailRequest', {
            url:'/email',
            templateUrl: 'partials/settings/changeEmail/changeEmail.html',
            controller: 'changeEmailRequestController',
            controllerAs:'vm',
            title: 'Settings'
        })
        .state('settings.changeEmailConfirmation', {
                controller: 'changeEmailConfirmationController',
                controllerAs: 'vm',
                url: '/settings/change-email/confirmation/{link}',
            title: 'Settings'
        })
        .state('settings.changePassword', {
            url:'/password',
            templateUrl: 'partials/settings/changePassword/changePassword.html',
            controller: 'changePasswordController',
            controllerAs:'vm',
            title: 'Settings'
        })
    ;
    $urlRouterProvider.otherwise('/home');
});