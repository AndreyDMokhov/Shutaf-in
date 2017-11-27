"use strict";
app.component("dealComponent", {
    templateUrl: "partials/deal/deal.component.html",
    bindings: {},
    controllerAs: "vm",
    controller: function ($filter, $state, $sessionStorage, $uibModal, dealModel) {
        var vm = this;

        var profile = $sessionStorage.userProfile;
        // var userId = profile.userId;

        vm.deals = [];
        vm.tabs = [];

        function init() {
            debugger;
            dealModel.getDeals().then(
                function (success) {
               /* if(success.data.data === null){
                    $state.go('error', {code: 404});
                }*/
                vm.deals = success.data.data;
                console.log(vm.deals);
            });


        };


        /* dealModel.dealTabsGet(userId).then(
         function (success) {
         if (success.data.data === null) {
         $state.go('error', {code: 404});
         }
         vm.tabs = success.data.data;
         }
         );*/


        vm.isDeal = function () {
            return deals;
        };
        //    init();

        vm.getCurrentStateName = function () {

            return $state.$current.name;

        };

        vm.selectedTab = function (tab) {
            $state.go(tab.state);
        };

        vm.addTab = function () {
            var modalInstance = $uibModal.open({
                animation: true,
                component: 'modalComponent',
                resolve: {}
            });
            modalInstance.result.then(function (newName) {
                var newTabName = newName;
                console.log(newTabName);
            }, function () {

            });
        };

        vm.removeTab = function () {
            console.log('Removed');
        };
        init();
    }
});

app.component('modalComponent', {
    templateUrl: 'ModalContent.html',
    bindings: {

        close: '&',
        dismiss: '&'
    },
    controllerAs: "vm",
    controller: function () {

        var vm = this;

        vm.ok = function () {

            vm.close({$value: vm.newTabName});
        };

        vm.cancel = function () {
            console.log("Hi");
            vm.dismiss({$value: 'cancel'});
        };

    }

});