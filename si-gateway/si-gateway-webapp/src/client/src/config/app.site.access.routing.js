app.factory('siteAssessRouting', function ($state,
                                           $stateParams,
                                           accountStatus,
                                           $sessionStorage) {

    function navigate(defaultStateName, defaultStateParams) {
        debugger;
        if ($sessionStorage.accountStatus === accountStatus.Statuses.CONFIRMED){
            console.log('Account status: ' + $sessionStorage.accountStatus);
            console.log('Navigating to \'settings.personal\'');

            $state.go('settings.personal', {}, {reload: true});
        } else if ($sessionStorage.accountStatus === accountStatus.Statuses.COMPLETED_USER_INFO){

            console.log('Account status: ' + $sessionStorage.accountStatus);
            console.log('Navigating to \'questionsTab.requiredQuestions\'');

            $state.go('questionsTab.requiredQuestions', {}, {reload: true});
        } else {
            console.log('Account status: ' + $sessionStorage.accountStatus);
            console.log('Navigating to \'' + defaultStateName + '\'');
            if (!defaultStateName) {
                defaultStateName = 'home';
            }

            if (!defaultStateParams) {
                defaultStateParams = {};
            }

            $state.go(defaultStateName, defaultStateParams, {reload: true});
        }
    }

    return {
        navigate : navigate
    };

});