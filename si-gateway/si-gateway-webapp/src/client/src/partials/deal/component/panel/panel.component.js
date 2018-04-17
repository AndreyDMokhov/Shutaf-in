"use strict";
app.component('panelComponent', {
    templateUrl: 'partials/deal/component/panel/panel.component.html',
    bindings: {
        documents: "<",
        getDeals: "=",
        panelId: '<',
        statusDeal: '<'
    },
    controllerAs: 'vm',
    controller: function (panelModel,
                          $uibModal,
                          $scope,
                          $timeout,
                          $state,
                          $sessionStorage,
                          uiNotification,
                          $filter,
                          documentTypes,
                          $window,
                          FILE_MAX_SIZE_MB) {
        var vm = this;
        vm.documentsShow = [];
        var componentType = 'file';
        vm.size = FILE_MAX_SIZE_MB * 1024;


        vm.$onChanges = function () {
            vm.documentsShow = vm.documents;
            for (var i = 0; i < vm.documentsShow.length; i++) {
                vm.documentsShow[i].createdDate = dateFormat(vm.documentsShow[i].createdDate);
                vm.documentsShow[i].extension = documentTypes.TYPES[vm.documentsShow[i].documentType];
            }
        };

        function dateFormat(dateNotFormated) {
            var date = new Date(dateNotFormated);
            return date.getDate() + '-' + date.getMonth() + '-' + date.getFullYear();
        }

        vm.openDocument = function (document) {
            panelModel.getDocument(document.documentId).then(
                function (success) {
                    var documentById = success.data.data;
                    if (documentById.documentTypeId === 2) {
                        openPDFonNewTab(documentById.fileData);
                    }
                    else
                        modalInput(documentById);
                },
                function (error) { }
            );
        };

        function modalInput(documentById) {
            var modalInstance = $uibModal.open({
                animation: true,
                component: 'documentDealComponent',
                controllerAs: "vm",
                windowClass: 'app-modal-window',
                resolve: {
                    docId: function () {
                        return documentById;
                    }
                }
            });
        }

        vm.fileInfo = {};
        $scope.onLoad = function (e, reader, file, fileList, fileOjects, fileObj) {
            $timeout(function () {
                vm.showFileName = true;
            });
        };

        vm.addDocument = function () {
            var type = 'creation';
            var modalInstance = $uibModal.open({
                animation: true,
                component: 'modalComponent',
                size: 'sm',
                resolve: {
                    type: function () {
                        return {type: type, component: componentType, filename: vm.fileInfo.filename};
                    }
                }
            });
            modalInstance.result.then(
                function (newName) {
                    if (newName === undefined) {
                        newName = vm.fileInfo.filename.slice(0, vm.fileInfo.filename.lastIndexOf('.'));

                    }
                    var uploadedDocument = {};
                    uploadedDocument.dealPanelId = vm.panelId;
                    uploadedDocument.fileData = vm.fileInfo.base64;
                    uploadedDocument.documentTypeId = getDocumentTypeByFileExtension(vm.fileInfo.filename);
                    uploadedDocument.documentTitle = newName;
                    if (vm.size < vm.fileInfo.filesize / 1024) {
                        uiNotification.show($filter('translate')('Deal.panel.file-max-size', {size: vm.size / 1024}), 'warn');
                        return;
                    }

                    panelModel.addDocument(uploadedDocument).then(
                        function (success) {
                            vm.getDeals(vm.panelId);
                            vm.showFileName = false;
                            uiNotification.show($filter('translate')('Deal.message.documentSaved'), 'success');
                        },
                        function (error) { }
                    );
                });
        };

        vm.deleteDocument = function (document) {
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
                panelModel.deleteDocument(document.documentId).then(
                    function (success) {
                        vm.getDeals(vm.panelId);
                    },
                    function (error) { }
                );
            });
        };

        vm.renameDocument = function (document) {
            var type = 'rename';
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
            modalInstance.result.then(function (newName) {
                if (newName === undefined) {
                    return;
                }
                var param = {title: newName};
                panelModel.renameDocument(document.documentId, param).then(
                    function (success) {
                        vm.getDeals(vm.panelId);
                    },
                    function (error) {}
                );
            });
        };

        function getDocumentTypeByFileExtension(filename) {
            var objectTypesExtensions = $sessionStorage.documentTypes;
            var extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length);
            if (extension === 'jpeg') {
                extension = 'jpg';
            }
            return Object.keys(objectTypesExtensions)[Object.values(objectTypesExtensions).indexOf(extension.toUpperCase())];
        }

        vm.downloadDocument = function (doc) {
            panelModel.getDocument(doc.documentId).then(
                function (success) {
                    $timeout(function () {
                        saveFile(success.data.data);
                    });
                },
                function (error) { }
            );
        };

        function saveFile(document) {
            var contentType = 'text/plain',
                blob = b64toBlob(document.fileData, contentType),
                filename = document.documentTitle + '.' + $sessionStorage.documentTypes[document.documentTypeId].toLowerCase();
            saveAs(blob, filename);
        }

        function b64toBlob(b64Data, contentType) {
            contentType = contentType || '';
            var sliceSize = sliceSize || 512;

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

        function openPDFonNewTab(base64) {
            var currentApplicationMIME = 'application/pdf';
            var blob = b64toBlob(base64, currentApplicationMIME);
            var pdfUrl = window.URL.createObjectURL(blob);
            window.open(pdfUrl, '_self');
        }
    }

});
