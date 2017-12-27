"use strict";
app.component('palletComponent', {
    templateUrl: 'partials/deal/deal.component/pallet.component/pallet.component.html',
    bindings: {
        documents: "<"
    },
    controllerAs: 'vm',
    controller: function (palletModel, $uibModal) {
        var vm = this;
        vm.documentsShow = [];


        vm.$onChanges = function () {
            vm.documentsShow = vm.documents;
            for (var i = 0; i < vm.documentsShow.length; i++) {
                vm.documentsShow[i].createdDate = dateFormat(vm.documentsShow[i].createdDate);
            }
        };

        vm.addDocument = function () {

        };
        function dateFormat(dateNotFormated) {
            var date = new Date(dateNotFormated);
            return date.getDate() + '-' + date.getMonth() + '-' + date.getFullYear();
        }

        vm.openDocument = function (doc) {
            modalInput(doc);
        };

        function modalInput(docId) {
            var modalInstance = $uibModal.open({
                animation: true,
                component: 'documentDealComponent',
                size: "lg",
                controllerAs: "vm",
                resolve: {
                    doc: function () {
                        return docId;
                    }
                }
            });
            modalInstance.result.then(function (newName) {


            }, function () {
            });
        }
    }

});
