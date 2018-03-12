app.component('dealLeaveComponent', {
    templateUrl: "partials/deal/leave/deal.leave.component.html",
    bindings: {
        dealInfo: '<'
    },
    controllerAs: "vm",
    controller: function (dealPresentationModel, dealStatus, $state, $filter, notify, $uibModal) {

        var vm = this;
        var componentType = 'deal';

        function isInDeal() {
            return Object.keys(vm.dealInfo).length && vm.dealInfo.statusId === dealStatus.Status.ACTIVE;
        }

        function leaveDeal() {
            var type = 'remove';
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
                dealPresentationModel.leaveDeal(vm.dealInfo.dealId).then(
                    function (success) {
                        notify.set($filter('translate')("Deal.leaving"));
                        $state.go('home');
                    },
                    function (error) {
                        notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                    }
                );


            });
        }

        vm.leaveDeal = leaveDeal;
        vm.isInDeal = isInDeal;
    }
});