"use strict";
app.component('headerComponent', {
        templateUrl: 'layout/header/header.component.html',
        bindings: {},
        controllerAs: 'vm',
        controller: function ($rootScope,
                              languageService,
                              sessionService,
                              $filter,
                              $sessionStorage,
                              $state,
                              $window,
                              initializationService,
                              webSocketService,
                              accountStatus,
                              sessionStorageObserver) {

            var vm = this;
            vm.userProfile = {};

            vm.currentAccountStatus = ($sessionStorage.accountStatus != null) ? $sessionStorage.accountStatus : 0;

            vm.accountStatuses = [];
            vm.accountStatuses['NEW'] = accountStatus.Statuses.NEW;
            vm.accountStatuses['CONFIRMED'] = accountStatus.Statuses.CONFIRMED;
            vm.accountStatuses['COMPLETED_USER_INFO'] = accountStatus.Statuses.COMPLETED_USER_INFO;
            vm.accountStatuses['COMPLETED_REQUIRED_MATCHING'] = accountStatus.Statuses.COMPLETED_REQUIRED_MATCHING;
            vm.accountStatuses['DEAL'] = accountStatus.Statuses.DEAL;
            vm.accountStatuses['BLOCKED'] = accountStatus.Statuses.BLOCKED;

            function userProfileInitialized() {
                vm.currentAccountStatus = ($sessionStorage.accountStatus != null) ? $sessionStorage.accountStatus : 0;
                vm.userProfile = angular.copy($sessionStorage.userProfile);
            }

            sessionStorageObserver.registerServiceObserver(userProfileInitialized);

            vm.sessionService = sessionService;
            vm.initialization = {};
            function init() {
                initializationService.initializeLanguages().then(function (languages) {
                    vm.initialization.languages = languages;
                    languageService.setFrontendLanguage($sessionStorage.currentLanguage);
                    if ($sessionStorage.sessionId) {
                        initializationService.initializeApplication().then(function () {
                            webSocketService.getConnection();
                        });
                    }
                });
            }


            function search(fullName) {
                $state.go('userSearch', {'name': fullName});
            }


            function setLanguageCode(languageId) {
                languageService.updateUserLanguage(languageId);
                $state.go('home', {}, {reload: true});
                initializationService.initializeApplication();
            }

            function getUserImage() {
                if (!vm.userProfile.userImage) {
                    return '../../images/default_avatar.png';
                }
                else {
                    return 'data:image/jpeg;base64,' + vm.userProfile.userImage;
                }
            }

            function resolveCurrentLanguage() {
                var language = languageService.getLanguage($sessionStorage.currentLanguage);
                if (language === undefined || language === null) {
                    return {languageNativeName: 'English'};
                }
                return language;
            }

            init();

            vm.setLanguageCode = setLanguageCode;
            vm.getUserImage = getUserImage;
            vm.search = search;
            vm.resolveCurrentLanguage = resolveCurrentLanguage;
        }
    }
);