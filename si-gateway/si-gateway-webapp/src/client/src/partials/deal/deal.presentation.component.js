app.component("dealPresentationComponent", {
    templateUrl: "partials/deal/deal.presentation.component.html",
    bindings: {},
    controllerAs: "vm",
    controller: function ($filter, $state, $sessionStorage, $uibModal, dealPresentationModel) {

        var vm = this;
        vm.deals = [];

        vm.selectedDeal = function (deal) {

            vm.dealId = deal.dealId;
            vm.dealStatus = deal.statusId;
            vm.dealTabClicked=true;
        };

        function getDeal() {
          return dealPresentationModel.getDeals();
        }

        function init() {
            vm.deals = getDeal();
        }


        init();
    }
});
