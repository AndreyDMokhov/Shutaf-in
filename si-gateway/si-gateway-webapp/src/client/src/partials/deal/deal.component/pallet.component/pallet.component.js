"use strict";
app.component('palletComponent', {
    templateUrl: 'partials/deal/deal.component/pallet.component/pallet.component.html',
    bindings: {
        documents: "<"
    },
    controllerAs: 'vm',
    controller: function (palletModel) {
        var vm = this;

        vm.$onChanges = function () {
            vm.documents;
        };



    }
});
