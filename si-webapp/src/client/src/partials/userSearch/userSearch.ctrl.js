app.controller("userSearchController", function($state, $sessionStorage, notify, sessionService, userSearchModel, $stateParams, $filter){
    var vm = this;

    vm.fullName;
    vm.userSearchList = {};
    
    function activate() {
        userSearch();
    }

    function userSearch() {

        if(vm.fullName === null)
            notify.set($filter('translate')("Search.noRequest"), {type: 'error'});

        userSearchModel.userSearch(vm.fullName).then(

            function (success) {

                vm.userSearchList=success;
                // if(vm.userSearchList.toString() == "")
                //     notify.set($filter('translate')("Search.notFound"), {type: 'error'});

            }, function (error) {
                    notify.set($filter('translate')("Search.notFound"), {type: 'error'});
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});

                if (error.data.error.errorTypeCode === 'AUT') {

                    $state.go("error", {'code': '401'});
                } else {

                    notify.set($filter('translate')("Search.notFound"), {type: 'error'});
                }
            })
    };

    activate();

    vm.userSearch=userSearch;
})