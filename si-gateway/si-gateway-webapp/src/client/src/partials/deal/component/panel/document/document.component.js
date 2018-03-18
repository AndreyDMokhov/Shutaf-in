"use strict";
app.component('documentDealComponent', {
    templateUrl: 'partials/deal/component/panel/document/document.component.html',
    bindings: {
        close: '&',
        dismiss: '&',
        resolve: '<'
    },
    controllerAs: 'vm',
    controller: function (documentModel, $scope, $sce, $window) {
        var vm = this;
        vm.document = {};
        vm.fullDocument = vm.resolve.docId;
        vm.getImage = function () {
            if (!vm.fullDocument.fileData) {
                return '../../images/default_avatar.png';
            }
            else {
                return 'data:image/jpeg;base64,' + vm.fullDocument.fileData;
            }
        };
    }
});