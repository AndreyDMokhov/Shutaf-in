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
                url: '/users'
            })
        .state('userProfile',
            {
                template: '<user-profile-component></user-profile-component>',
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
            template: '<user-search-component></user-search-component>',
            url: '/users/search?{name}'
        })
        .state('questionsTab.requiredQuestions', {
            template:'<required-questions-component></required-questions-component>',
            url: '/required-questions'
        })
        .state('questionsTab.extendedQuestions', {
            template:'<extended-questions-component></extended-questions-component>',
            url: '/extended-questions'
        })
        /*Deal*/
        .state('deal', {
            url: '/deal',
            template: '<deal-presentation-component></deal-presentation-component>'
        })
        .state('deal.document', {
            url: '/document',
            template: '<document-deal-component></document-deal-component>'
        })

        /* Settings */
        .state('settings', {
            abstract: true,
            url: '/settings',
            template: '<settings-component></settings-component>'
        })
        .state('settings.personal', {
            url: '/personal',
            template: '<user-settings-component></user-settings-component>'
        })
        .state('settings.changeEmailRequest', {
            url: '/email',
            template: '<change-email-component></change-email-component>'

        })
        .state('settings.changeEmailConfirmation', {
            controller: 'changeEmailConfirmationController',
            controllerAs: 'vm',
            url: '/settings/change-email/confirmation/{link}'
        })
        .state('settings.changePassword', {
            url: '/password',
            template: '<change-password-component></change-password-component>'
        })
        .state("chat", {
            url: "/chat",
            template: '<messenger-ui-component></messenger-ui-component>'
        })
    ;
    $urlRouterProvider.otherwise('/home');
});