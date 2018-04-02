app.component("dealPresentationComponent", {
    templateUrl: "partials/deal/deal.presentation.component.html",
    bindings: {},
    controllerAs: "vm",
    controller: function ($filter,
                          $state,
                          $sessionStorage,
                          $uibModal,
                          dealPresentationModel,
                          notify,
                          dealUserStatus,
                          dealStatus,
                          browserTitle) {

        browserTitle.setBrowserTitleByFilterName('Deal.title');

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
                    vm.showLoading = false;
                    vm.dealTabClicked = true;
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                });
        };

        function getDeals() {
            dealPresentationModel.getDeals().then(function (success) {
                vm.deals = success.data.data;
            });
        }

        vm.getDealSuffix = function (deal) {
            if (deal.statusId === dealStatus.Status.INITIATED && deal.userStatusId === dealUserStatus.Status.ACTIVE) {
                return $filter('translate')('Deal.deal.status.inactive.wait-for-approval');
            }

            if (deal.statusId === dealStatus.Status.INITIATED) {
                return $filter('translate')('Deal.deal.status.inactive');
            }

            if(deal.userStatusId === dealUserStatus.Status.PENDING) {
                return $filter('translate')('Deal.deal.status.inactive');
            }

            if (deal.userStatusId === dealUserStatus.Status.ACTIVE) {
                return $filter('translate')('Deal.deal.status.active');
            }

            if (deal.userStatusId === dealUserStatus.Status.LEAVED) {
                return $filter('translate')('Deal.deal.status.archive');
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
                        notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                    });
            });
        };
        getDeals();
    }
});
