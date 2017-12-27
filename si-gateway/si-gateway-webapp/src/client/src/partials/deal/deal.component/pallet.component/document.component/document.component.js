"use strict";
app.component('documentDealComponent', {
    templateUrl: 'partials/deal/deal.component/pallet.component/document.component/document.component.html',
    bindings: {
        close: '&',
        dismiss: '&',
        resolve: '<'
    },
    controllerAs: 'vm',
    controller: function (documentModel) {

        var vm = this;
        vm.document ={};

        vm.$onInit = function () {

            var document = documentModel.getDocument(vm.resolve.doc);
            vm.document = document;
            vm.document.createdDate =dateFormat(document.createdDate);

        };

        vm.getImage = function () {
            debugger;
            if (!vm.document.fileData) {
                return '../../images/default_avatar.png';
            }
            else {
                return 'data:image/jpeg;base64,' + vm.document.fileData;
            }
        };

        function dateFormat(dateNotFormated) {
            var date= new Date(dateNotFormated);
            return  date.getDate() + '-' + date.getMonth() + '-'+ date.getFullYear();
        }

        vm.ok = function () {

            vm.close({$value: vm.newTabName});
        };

        vm.cancel = function () {

            vm.dismiss({$value: 'cancel'});
        };
    }
});




