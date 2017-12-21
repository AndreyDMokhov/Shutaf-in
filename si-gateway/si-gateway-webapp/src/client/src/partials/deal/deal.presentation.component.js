app.component("dealPresentationComponent", {
    templateUrl: "partials/deal/deal.presentation.component.html",
    bindings: {},
    controllerAs: "vm",
    controller: function ($filter, $state, $sessionStorage, $uibModal, dealPresentationModel) {

        var vm = this;
        vm.deals = [];
        vm.dealInfo = {};

        vm.selectedDeal = function (deal) {

            dealPresentationModel.getDealInfo(deal.dealId).then(
                function (success) {
                    vm.dealInfo = success.data.data;
                    vm.dealTabClicked=true;

                },
                function (err) {
                    console.log(err);

                });
        };

        function getDeal() {
            dealPresentationModel.getDeals().then(function (success) {
                vm.deals = success.data.data;
                console.log(vm.deals);
            });
        }

        function init() {
            vm.deals = getDeal();
        }

        init();
    }
});
