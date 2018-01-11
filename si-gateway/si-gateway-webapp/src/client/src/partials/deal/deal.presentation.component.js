app.component("dealPresentationComponent", {
    templateUrl: "partials/deal/deal.presentation.component.html",
    bindings: {},
    controllerAs: "vm",
    controller: function ($filter, $state, $sessionStorage, $uibModal, dealPresentationModel) {

        var vm = this;
        vm.deals = [];
        vm.dealInfo = {};

        vm.showSelectedDeal = function (deal) {
            vm.showLoading = true;

            dealPresentationModel.getDealInfo(deal.dealId).then(
                function (success) {
                    vm.dealInfo = success.data.data;
                    vm.dealTabClicked = true;
                    vm.showLoading = false;
                },
                function (error) {
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                });
        };

        function getDeals() {
            dealPresentationModel.getDeals().then(function (success) {
                vm.deals = success.data.data;
            });
        }

        vm.renameDeal = function (size, type, deal) {
            var modalInstance = $uibModal.open({
                animation: true,
                component: 'modalComponent',
                size: size,
                resolve: {
                    type: function () {
                        return type;
                    }
                }
            });
            modalInstance.result.then(function (newName) {
                if (newName === undefined) {
                    newName = 'Deal';
                }
                var param = {title: newName};
                dealPresentationModel.renameDeal(deal.dealId, param).then(
                    function (success) {
                        getDeals();
                    },
                    function (error) {
                        notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                    }
                );
            });
        };
        getDeals();
    }
});
