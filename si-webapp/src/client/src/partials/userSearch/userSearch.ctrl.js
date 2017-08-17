app.controller("userSearchController", function ($state, $sessionStorage, notify, sessionService, userSearchModel, $stateParams, $filter) {
    var vm = this;

    vm.fullName;
    vm.userSearchList = {};

    function activate() {
        userSearch();
    }

    function userSearch() {

        userSearchModel.userSearch(vm.fullName).then(
            function (success) {

                vm.userSearchList = success;

                if (vm.userSearchList.length > 0) {
                    return;
                }else {
                    alert("Users not found");
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