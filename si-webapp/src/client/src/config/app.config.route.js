'use strict';
app.config(function ($stateProvider, $urlRouterProvider) {
    $stateProvider
        .state('home',
            {
                templateUrl: 'partials/home/home.html',
                controller: 'homeController',
                controllerAs: 'vm',
                url: '/home'
            })
        .state('about',
            {
                templateUrl: 'partials/about/about.html',
                controller: 'aboutController',
                controllerAs: 'vm',
                url: '/about'
            })

        .state('registration',
            {
                templateUrl: 'partials/registration/registration.html',
                controller: 'userRegistration',
                controllerAs: 'vm',
                url: '/registration/request'
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
                url: '/users'
            })
        .state('userProfile',
            {
                templateUrl: 'partials/userProfile/userProfile.html',
                controller: 'userProfileController',
                controllerAs: 'vm',
                url: '/profile/{id:int}'
            })

        .state('login',
            {
                templateUrl: 'partials/login/login.html',
                controller: 'loginController',
                controllerAs: 'vm',
                url: '/login'
            })
        .state('logout', {
                controller: 'logoutController',
                url: '/logout'
        })
        .state('resetPasswordRequest', {
                templateUrl: 'partials/resetPassword/resetPassword.request.html',
                url: '/reset-password/request'
        })
        .state('resetPasswordNewPassword', {
                url: '/reset-password/confirmation/{link}',
                templateUrl: 'partials/resetPassword/resetPassword.confirmation.html'
        })
        .state('userSearch', {
                templateUrl: 'partials/userSearch/userSearch.html',
                controller: 'userSearchController',
                controllerAs: 'vm',
                url: '/users/search?{name}'
        })
        .state('questions', {
                templateUrl:'partials/questions/questions.html',
                controller: 'questionsCtrl',
                controllerAs: 'vm',
                url: '/questions'
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
            controllerAs: 'vm'
        })
        .state('settings.changeEmailRequest', {
            url:'/email',
            templateUrl: 'partials/settings/changeEmail/changeEmail.html',
            controller: 'changeEmailRequestController',
            controllerAs:'vm'
        })
        .state('settings.changeEmailConfirmation', {
                controller: 'changeEmailConfirmationController',
                controllerAs: 'vm',
                url: '/settings/change-email/confirmation/{link}'
        })
        .state('settings.changePassword', {
            url:'/password',
            templateUrl: 'partials/settings/changePassword/changePassword.html',
            controller: 'changePasswordController',
            controllerAs:'vm'
        })
    ;
    $urlRouterProvider.otherwise('/home');
});