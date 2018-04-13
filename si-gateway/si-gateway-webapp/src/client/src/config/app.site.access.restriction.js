/**
 * Created by evgeny on 1/3/2018.
 */

//https://ui-router.github.io/guide/ng1/migrate-to-1_0#state-change-events
app.run(function ($rootScope,
                  $state,
                  notify,
                  $filter,
                  sessionService,
                  $sessionStorage) {

    var allowedUnauthenticated = [
        'home',
        'login',
        'logout',
        'registration',
        'about',
        'registrationConfirmation',
        'userProfile',
        'resetPasswordRequest',
        'resetPasswordNewPassword',
        'dealInitializeConfirmation',
        'dealAddUserConfirmation',
        'dealRemoveUserConfirmation'
    ];

    function isAllowedToNavigate(toState) {
        if (sessionService.isAuthenticated()) {
            return true;
        }

        for (var i = 0; i < allowedUnauthenticated.length; i++) {
            if (allowedUnauthenticated[i] === toState.name) {
                return true;
            }
        }
        return false;
    }


    $rootScope.$on('$stateChangeStart', function (evt, toState, toParams, fromState, fromParams) {

        if (!isAllowedToNavigate(toState)) {
            notify.set($filter('translate')('Error.AUT'), {type: 'error'});
            evt.preventDefault();
            $state.go('home');
            return;
        }

        //If user tries to access page manually by writing URL in the address bar
        if ($sessionStorage.accountStatus !== undefined && toParams.accessibleToAccountStatus > $sessionStorage.accountStatus) {
            switch ($sessionStorage.accountStatus) {
                case 2 :
                    evt.preventDefault();
                    notify.set($filter('translate')('SiteAccess.info.settings'), {type: 'info'});
                    $state.go('settings.personal');
                    break;
                case 3 :
                    evt.preventDefault();
                    notify.set($filter('translate')('SiteAccess.questionnaire.settings'), {type: 'info'});
                    $state.go('questionsTab.requiredQuestions');
                    break;
                default :
                    evt.preventDefault();
                    $state.go('home');
                    break;
            }
        }
    });
});