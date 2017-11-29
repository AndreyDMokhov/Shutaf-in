"use strict";
app.component("dealComponent", {
    templateUrl: "partials/deal/deal.component.html",
    bindings: {},
    controllerAs: "vm",
    controller: function ($filter, $state, $sessionStorage, $uibModal, dealModel) {
        var vm = this;

        var profile = $sessionStorage.userProfile;
        // var userId = profile.userId;

        var deals = [
            {
                dealId: 0,
                title: 'My deal',
                statusId: 2,
                users: [
                    {
                        userId: 1,
                        userName: "Andrey"
                    },
                    {
                        userId: 2,
                        userName: "Oksana"
                    }
                ]

            },
            {
                dealId: 1,
                title: 'Archive',
                statusId: 3,
                users: [
                    {
                        userId: 1,
                        userName: "Ivan"
                    },
                    {
                        userId: 2,
                        userName: "Maria"
                    }
                ]
            }];   //$sessionStorage.deals
        vm.dealTitle = [];
        vm.dealsUsers = [];
        vm.tabs = [];

        function getDealsTitle() {
            var titles = [];
            for (var i = 0; i < deals.length; i++) {
                titles.push({
                    'dealId': deals[i].dealId,
                    'title': deals[i].title,
                    'statusId': deals[i].statusId
                });
            }
            return titles;
        }

        function getDealsUsers(dealId) {
            debugger;
            var users = [];
            for(var i=0; i<deals.length; i++){
                if(deals[i].dealId === dealId){
                    users = deals[i].users;
                }
            }

            console.log(users);
            return users;
        }

        function init() {

            vm.dealTitle = getDealsTitle();
            vm.dealsUsers = getDealsUsers(0);


        }


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