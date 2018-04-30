app.component("dealPresentationComponent", {
    templateUrl: "partials/deal/deal.presentation.component.html",
    bindings: {},
    controllerAs: "vm",
    controller: function ($filter,
                          $state,
                          $sessionStorage,
                          $uibModal,
                          dealPresentationModel,
                          dealUserStatus,
                          dealStatus,
                          browserTitleService,
                          uiNotification) {

        browserTitleService.setBrowserTitleByFilterName('Deal.title');

        var vm = this;
        vm.showEditDeal = true;
        vm.showDeleteDeal = false;
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
                    vm.showLoading = false;
                    vm.dealTabClicked = true;
                });
        };

        function getDeals() {
            dealPresentationModel.getDeals().then(function (success) {
                vm.deals = success.data.data;
            });
        }

        vm.getDealSuffix = function (deal) {
            vm.showEditDeal = true;
            vm.showDeleteDeal = false;
            if (deal.statusId === dealStatus.Status.INITIATED && deal.userStatusId === dealUserStatus.Status.ACTIVE) {
                return $filter('translate')('Deal.deal.status.inactive.wait-for-approval');
            } else if (deal.statusId === dealStatus.Status.INITIATED) {
                return $filter('translate')('Deal.deal.status.inactive');
            } else if (deal.userStatusId === dealUserStatus.Status.PENDING) {
                return $filter('translate')('Deal.deal.status.inactive');
            } else if (deal.userStatusId === dealUserStatus.Status.LEAVED || deal.statusId === dealStatus.Status.ARCHIVE) {
                vm.showEditDeal = false;
                vm.showDeleteDeal = true;
                return $filter('translate')('Deal.deal.status.archive');
            } else if (deal.userStatusId === dealUserStatus.Status.ACTIVE) {
                return $filter('translate')('Deal.deal.status.active');
            }
        };

        vm.renameDeal = function (deal) {
            var componentType = 'deal',
                type = 'rename';
            var modalInstance = $uibModal.open({
                animation: true,
                component: 'modalComponent',
                size: 'sm',
                resolve: {
                    type: function () {
                        return {type: type, component: componentType};
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
                    });
            });
        };

        vm.deleteDeal = function (deal) {
            var componentType = 'deal',
                type = 'remove';
            var modalInstance = $uibModal.open({
                animation: true,
                component: 'modalComponent',
                size: 'sm',
                resolve: {
                    type: function () {
                        return {type: type, component: componentType, filename: document.title};
                    }
                }
            });
            modalInstance.result.then(function () {
                dealPresentationModel.deleteDeal(deal.dealId).then(
                    function (success) {
                        var message = '<p>' + $filter("translate")("Deal.deleting") + ': <strong>' + deal.title + '</strong></p>';
                        uiNotification.show(message, 'info', true);
                        var index = vm.deals.indexOf(deal);
                        vm.deals.splice(index, 1);
                    },
                    function (error) {
                    });
            });
        };

        getDeals();
    }
});
