app.controller("userSearchController", function($state, $sessionStorage, $event, sessionService, userSearchModel, $stateParams){
    var vm = this;

    vm.userQuery = {};
    vm.listUsersByQuery = {};

    function userSearch() {
        vm.dataLoading = true;
        var urlLink = $stateParams.link;
        if (urlLink === undefined || urlLink === null || urlLink === "") {
            $state.go("error", {'code': '404'});
        }
        if(vm.userRequest === null)
            return alert("Enter search query");
        userSearchModel.userSearch(vm.userQuery).then(

            function (success) {
                for(user in vm.listUsersByQuery){

                }


            }, function (error) {

                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});

            })
    };

    userSearch();
})