"use strict";
app.component('dealContentComponent', {
    templateUrl: 'partials/deal/dealContent/dealContent.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function (dealModel) {

        var vm = this;
        vm.pallets = [];

        function init() {
           vm.pallets = dealModel.getPallets();/*.then(
                function (success) {
                    if (success.data.data === null) {
                        $state.go('error', {code: 404});
                    }
                    vm.pallets = success.data.data;
                }
            );*/
            console.log(vm.pallets);
        }
    }
});
