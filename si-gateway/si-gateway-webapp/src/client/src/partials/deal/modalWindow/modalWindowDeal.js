app.component('modalComponent',
    {
        templateUrl: 'partials/deal/modalWindow/ModalWindowDeal.html',
        bindings: {
            close: '&',
            dismiss: '&',
            resolve: '<'
        },
        controllerAs: "vm",
        controller: function ($filter) {
            var vm = this;
            var translatedComponent = $filter('translate')("Deal." + vm.resolve.type.component);
            vm.componentData = {
                component: translatedComponent
            };
            vm.ok = function () {

                vm.close({$value: vm.newTabName});
            };
            vm.cancel = function () {
                vm.dismiss({$value: 'cancel'});
            };
        }

    });