app.component('dealComponent', {
    templateUrl: 'partials/deal/deal.component/deal.component.html',
    bindings: {
        dealInfo: '<'

    },
    controllerAs: 'vm',
    controller: function (dealModel, $uibModal) {

        var vm = this;
        var namePanelDef = "Pallet";
        var ACTIVE = 2;                   //?????????????????/

        vm.isDealStatusActive = function () {

            return vm.statusDeal === ACTIVE;
        };
        vm.$onInit = function () {

            console.log(vm.dealInfo);
            vm.statusDeal = vm.dealInfo.statusId;
            vm.pallets = vm.dealInfo.panels;

        };
        vm.$onChanges = function () {
            vm.statusDeal = vm.dealInfo.statusId;
            vm.pallets = vm.dealInfo.panels;
        };

        vm.selectPallet = function (pallet) {
            vm.palletClicked = true;
            vm.documents = pallet.documents;
        };

        vm.addPallet = function (dealId) {

            modalInput(dealId);


        };

        function modalInput(dealId) {
            var modalInstance = $uibModal.open({
                animation: true,
                component: 'modalComponent',
                resolve: {}
            });
            modalInstance.result.then(function (newName) {
                if (newName === undefined) {
                    newName = namePanelDef;
                }

                var params = {dealId: dealId, title: newName};
                dealModel.addPallet(params).then(function (success) {
                    debugger;
                    console.log(success);
                    //vm.pallets.push(success);
                });

            }, function () {
            });
        }

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

            vm.dismiss({$value: 'cancel'});
        };

    }

});