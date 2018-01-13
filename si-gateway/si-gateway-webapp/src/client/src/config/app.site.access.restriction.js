/**
 * Created by evgeny on 1/3/2018.
 */

//https://ui-router.github.io/guide/ng1/migrate-to-1_0#state-change-events
app.run(function($rootScope, $sessionStorage, $state, notify, $filter) {
    $rootScope.$on('$stateChangeStart', function(evt, toState, toParams, fromState, fromParams) {

        //If user tries to access page manually by writing URL in the address bar
        if ($sessionStorage.accountStatus !== undefined && toParams.accessibleToAccountStatus > $sessionStorage.accountStatus){
            switch ($sessionStorage.accountStatus){
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