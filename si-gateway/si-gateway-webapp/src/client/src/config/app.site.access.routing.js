app.factory('siteAccessRouting', function ($state,
                                           $stateParams,
                                           accountStatus,
                                           $sessionStorage) {

    function navigate(defaultStateName, defaultStateParams) {
        if ($sessionStorage.accountStatus === accountStatus.Statuses.CONFIRMED){

            $state.go('settings.personal', {}, {reload: true});
        } else if ($sessionStorage.accountStatus === accountStatus.Statuses.COMPLETED_USER_INFO){

            $state.go('questionsTab.requiredQuestions', {}, {reload: true});
        } else {
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