"use strict";
app.component('palletComponent', {
    templateUrl: 'partials/deal/deal.component/pallet.component/pallet.component.html',
    bindings: {
        documents: "<",
        getDeals: "=",
        panelId: '<',
        statusDeal: '<'
    },
    controllerAs: 'vm',
    controller: function (palletModel, $uibModal, $scope, $timeout, $state, $sessionStorage, notify, $filter) {
        var vm = this;
        vm.documentsShow = [];
        var componentType = 'file';
        var typeDocument =
            {
                0: 'doc',
                1: 'docx',
                2: 'pdf',
                3: 'tif',
                4: 'jpg',
                5: 'gif',
                6: 'png'
            };

        vm.$onChanges = function () {
            vm.documentsShow = vm.documents;
            for (var i = 0; i < vm.documentsShow.length; i++) {
                vm.documentsShow[i].createdDate = dateFormat(vm.documentsShow[i].createdDate);
                vm.documentsShow[i].extension = typeDocument[vm.documentsShow[i].documentType];
            }
        };

        function dateFormat(dateNotFormated) {
            var date = new Date(dateNotFormated);
            return date.getDate() + '-' + date.getMonth() + '-' + date.getFullYear();
        }

        vm.openDocument = function (docId) {
            palletModel.getDocument(docId).then(
                function (success) {
                    var documentById = success.data.data;
                    modalInput(documentById);
                },
                function (error) {
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                }
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
                        return {type: type, component: componentType};
                    }
                }
            });
            modalInstance.result.then(
                function (newName) {
                    if (newName === undefined) {
                        newName = getDefaultName();
                    }
                    var uploadedDocument = {};
                    uploadedDocument.dealPanelId = vm.panelId;
                    uploadedDocument.fileData = vm.fileInfo.base64;
                    uploadedDocument.documentTypeId = getDocumentTypeByFileExtension(vm.fileInfo.filename);
                    uploadedDocument.documentTitle = newName;
                    palletModel.addDocument(uploadedDocument).then(
                        function (success) {
                            vm.getDeals(vm.panelId);
                            vm.showFileName = false;
                            notify.set($filter('translate')('Deal.message.documentSaved'), {type: 'success'});
                        },
                        function (error) {
                            notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});

                        }
                    );
                });
        };

        vm.deleteDocument = function (id) {
            var type = 'remove';
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
            modalInstance.result.then(function () {
                palletModel.deleteDocument(id).then(
                    function (success) {
                        vm.getDeals(vm.panelId);
                    },
                    function (error) {
                        notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                    }
                );
            });
        };

        vm.renameDocument = function (id) {
            var type = 'rename';
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
                    newName = getDefaultName();
                }
                var param = {title: newName};
                palletModel.renameDocument(id, param).then(
                    function (success) {
                        vm.getDeals(vm.panelId);
                    },
                    function (error) {
                        notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});

                    }
                );
            });
        };

        function getDefaultName() {
            var date = new Date;
            return "Document was created: " + date.toDateString() + " " + date.toLocaleTimeString();
        }

        function getDocumentTypeByFileExtension(filename) {
            var objectTypesExtentions = $sessionStorage.documentTypes;
            var extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length);
            if (extension === 'jpeg') {
                extension = 'jpg';
            }
            return Object.keys(objectTypesExtentions)[Object.values(objectTypesExtentions).indexOf(extension.toUpperCase())];
        }

        vm.downloadDocument = function (doc) {
            palletModel.getDocument(doc.documentId).then(
                function (success) {
                    $timeout(function () {
                        saveFile(success.data.data);
                    });
                },
                function (error) {
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});

                }
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
    }

});
