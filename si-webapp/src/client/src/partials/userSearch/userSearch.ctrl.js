app.controller("userSearchController", function ($state, $sessionStorage, notify, sessionService, userSearchModel, $stateParams, $filter) {
    var vm = this;

    vm.userSearchList = {};
    vm.fullName=$stateParams.name;

    function activate() {
        userSearch();
    }

    function userSearch() {
        alert(vm.fullName)

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