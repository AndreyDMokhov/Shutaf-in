'use strict';
app.config(function ($stateProvider, $urlRouterProvider, accountStatus) {
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
                url: '/profile/{userId:int}',
                params: {
                        accessibleToAccountStatus: accountStatus.Statuses.COMPLETED_USER_INFO   //3
                }
            })
        .state('myUserProfile',
            {
                template: '<my-user-profile-component></my-user-profile-component>',
                url: '/my-profile',
                params: {
                    accessibleToAccountStatus: accountStatus.Statuses.COMPLETED_USER_INFO   //3
                }
            })

        .state('login',
            {
                template: '<login-component></login-component>',
                url: '/login'
            })
        .state('logout', {
            controller: 'logoutController',
            url: '/logout',
            params: {
                accessibleToAccountStatus: accountStatus.Statuses.CONFIRMED     //2
            }
        })
        .state('resetPasswordRequest', {
            template: '<reset-password-request-component></reset-password-request-component>',
            url: '/reset-password/request',
            params: {
                accessibleToAccountStatus: accountStatus.Statuses.CONFIRMED     //2
            }
        })
        .state('resetPasswordNewPassword', {
            url: '/reset-password/confirmation/{link}',
            template: '<reset-password-confirmation-component></reset-password-confirmation-component>',
            params: {
                accessibleToAccountStatus: accountStatus.Statuses.CONFIRMED     //2
            }
        })
        .state('userSearch', {
            template: '<user-search-component></user-search-component>',
            url: '/users/search?{name}&{page}&{results}',
            params: {
                accessibleToAccountStatus: accountStatus.Statuses.COMPLETED_REQUIRED_MATCHING   //4
            }
        })
        .state('questionsTab', {
            abstract: true,
            template: '<questions-tab></questions-tab>',
            url: '/questions',
            params: {
                accessibleToAccountStatus: accountStatus.Statuses.COMPLETED_USER_INFO   //3
            }
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


        /* Settings */
        .state('settings', {
            abstract: true,
            url: '/settings',
            template: '<settings-component></settings-component>',
            params: {
                accessibleToAccountStatus: accountStatus.Statuses.CONFIRMED     //2
            }
        })
        .state('settings.personal', {
            url: '/personal',
            template: '<user-settings-component></user-settings-component>',
            params: {
                accessibleToAccountStatus: accountStatus.Statuses.CONFIRMED     //2
            }
        })
        .state('settings.changeEmailRequest', {
            url: '/email',
            template: '<change-email-component></change-email-component>',
            params: {
                accessibleToAccountStatus: accountStatus.Statuses.CONFIRMED     //2
            }
        })
        .state('settings.changeEmailConfirmation', {
            controller: 'changeEmailConfirmationController',
            controllerAs: 'vm',
            url: '/settings/change-email/confirmation/{link}',
            params: {
                accessibleToAccountStatus: accountStatus.Statuses.CONFIRMED     //2
            }
        })
        .state('settings.changePassword', {
            url: '/password',
            template: '<change-password-component></change-password-component>',
            params: {
                accessibleToAccountStatus: accountStatus.Statuses.CONFIRMED     //2
            }
        })
        .state('settings.matching', {
            url: '/matching',
            template: '<matching-settings-component></matching-settings-component>',
            params: {
                accessibleToAccountStatus: accountStatus.Statuses.CONFIRMED    //2
            }
        })
        .state("chat", {
            url: "/chat",
            template: '<messenger-ui-component></messenger-ui-component>',
            params: {
                accessibleToAccountStatus: accountStatus.Statuses.COMPLETED_REQUIRED_MATCHING   //4
            }
        })
    ;
    $urlRouterProvider.otherwise('/home');
});
