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
        vm.setVisibilityByCurrentType = function () {
            vm.currentType = {
                doc: false,
                pdf: false,
                image: false
            };
            if (vm.fullDocument.documentTypeId === 0 || vm.fullDocument.documentTypeId === 1) {
                vm.currentType.doc = true;
            }
            else if (vm.fullDocument.documentTypeId === 2) {
                vm.currentType.pdf = true;
            }
            else if (vm.fullDocument.documentTypeId >= 3 && vm.fullDocument.documentTypeId <= 6) {
                vm.currentType.image = true;
            }
            return vm.currentType;
        };
        vm.setVisibilityByCurrentType();
        vm.getImage = function () {
            if (!vm.fullDocument.fileData) {
                return '../../images/default_avatar.png';
            }
            else {
                return 'data:image/jpeg;base64,' + vm.fullDocument.fileData;
            }
        };

        function b64toBlob(b64Data, contentType, sliceSize) {
            contentType = contentType || '';
            sliceSize = sliceSize || 512;

            var byteCharacters = atob(b64Data);
            var byteArrays = [];

            for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
                var slice = byteCharacters.slice(offset, offset + sliceSize);

                var byteNumbers = new Array(slice.length);
                for (var i = 0; i < slice.length; i++) {
                    byteNumbers[i] = slice.charCodeAt(i);
                }

                var byteArray = new Uint8Array(byteNumbers);

                byteArrays.push(byteArray);
            }

            var blob = new Blob(byteArrays, {type: contentType});
            return blob;
        }



        vm.getPdfUrl = function () {
            debugger;
            var blob = b64toBlob(vm.fullDocument.fileData);
            var currentBlob = new Blob([blob], {type: 'application/pdf'});
            var pdfUrl = window.URL.createObjectURL(currentBlob);
            pdfUrl = $sce.trustAsUrl(pdfUrl);
            return pdfUrl;
        };

        // window.location.href(vm.pdfUrl);


    }
});