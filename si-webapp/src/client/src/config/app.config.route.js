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
                template: '<registration-component></registration-component>',
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
                template: '<login-component></login-component>',
                url: '/login'
            })
        .state('logout', {
                controller: 'logoutController',
                url: '/logout'
        })
        .state('resetPasswordRequest', {
                template: '<reset-password-request-component></reset-password-request-component>',
                url: '/reset-password/request'
        })
        .state('resetPasswordNewPassword', {
                url: '/reset-password/confirmation/{link}',
                template: '<reset-password-confirmation-component></reset-password-confirmation-component>'
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
            template: '<settings-component></settings-component>'
        })
        .state('settings.personal', {
            url:'/personal',
            template: '<user-settings-component></user-settings-component>'
        })
        .state('settings.changeEmailRequest', {
            url:'/email',
            template: '<change-email-component></change-email-component>'

        })
        .state('settings.changeEmailConfirmation', {
                controller: 'changeEmailConfirmationController',
                controllerAs: 'vm',
                url: '/settings/change-email/confirmation/{link}'
        })
        .state('settings.changePassword', {
            url:'/password',
            template: '<change-password-component></change-password-component>'
        })
    ;
    $urlRouterProvider.otherwise('/home');
});