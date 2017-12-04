app.component('dealComponent', {
    templateUrl: 'partials/deal/deal.component/deal.component.html',
    bindings: {
        dealId: '<',
        dealStatus: '<'
    },
    controllerAs: 'vm',
    controller: function (dealModel, $uibModal) {

        var vm = this;
        var namePallet = "Pallet";

        vm.$onChanges = function () {

            debugger;
            vm.pallets = dealModel.getPanels(vm.dealId);
        };

        vm.selectPallet = function (pallet) {
           vm.palletClicked=true;
           vm.documents = pallet.documents;
        };

        vm.addPallet = function (dealId) {

            modalInput(dealId);
            debugger;

        };

        function modalInput(dealId) {
            var modalInstance = $uibModal.open({
                animation: true,
                component: 'modalComponent',
                resolve: {}
            });
            modalInstance.result.then(function (newName) {
                namePallet = newName;
                dealModel.addPallet(dealId, namePallet);

            }, function () {});
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
            console.log("Hi");
            vm.dismiss({$value: 'cancel'});
        };

    }

});