app.controller("userSearchController", function ($state, $sessionStorage, notify, sessionService, userSearchModel, $stateParams, $filter) {
    var vm = this;

    vm.fullName;
    vm.userSearchList = {};

    function activate() {
        userSearch();
    }

    function userSearch() {

        if (vm.fullName === "")
            notify.set($filter('translate')("Search.noRequest"), {type: 'error'});

        userSearchModel.userSearch(vm.fullName).then(
            function (success) {

                vm.userSearchList = success;

                if (vm.userSearchList.length() > 0) {

                }


            }, function (error) {
                if (error === undefined || error === null) {
                    notify.set($filter('translate')('Error.SYS'), {type: 'error'});
                }

                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
            })
    };

    activate();

    vm.userSearch = userSearch;
})