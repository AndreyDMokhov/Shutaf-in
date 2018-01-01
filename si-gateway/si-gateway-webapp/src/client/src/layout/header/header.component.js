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
                              webSocketService) {

            var vm = this;
            vm.userProfile = {};

            //This object has to contain the same values as defined in AccountStatus.java
            vm.accountStatusEnum = {};
            vm.accountStatusEnum.NEW = 1;
            vm.accountStatusEnum.CONFIRMED = 2;
            vm.accountStatusEnum.COMPLETED_USER_INFO = 3;
            vm.accountStatusEnum.COMPLETED_REQUIRED_MATCHING = 4;

            vm.currentAccountStatus = ($sessionStorage.accountStatus != null) ? $sessionStorage.accountStatus : 0;

            vm.sessionService = sessionService;
            $rootScope.brand = "Shutaf-In";
            vm.initialization = {};
            function init() {
                initializationService.initializeLanguages().then(function (languages) {
                    vm.initialization.languages = languages;
                    languageService.setFrontendLanguage($sessionStorage.currentLanguage);
                    if ($sessionStorage.sessionId) {
                        initializationService.initializeApplication().then(function () {
                            vm.userProfile = $sessionStorage.userProfile;
                            $rootScope.brand = vm.userProfile.firstName + ' ' + vm.userProfile.lastName;
                            webSocketService.getConnection();
                        });
                    }
                });
            }


            function setLanguageCode(languageId) {
                languageService.updateUserLanguage(languageId);
                $state.go('home', {}, {reload: true});
                $window.location.reload();
            }

            function getUserImage() {
                if (!vm.userProfile.userImage) {
                    return '../../images/default_avatar.png';
                }
                else {
                    return 'data:image/jpeg;base64,' + vm.userProfile.userImage;
                }
            }

            init();

            vm.setLanguageCode = setLanguageCode;
            vm.getUserImage = getUserImage;

        }
    }
);