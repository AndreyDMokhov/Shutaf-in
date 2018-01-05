"use strict";
app.component('palletComponent', {
    templateUrl: 'partials/deal/deal.component/pallet.component/pallet.component.html',
    bindings: {
        documents: "<",
        getDeals: "=",
        panelId: '<'
    },
    controllerAs: 'vm',
    controller: function (palletModel, $uibModal, $scope, $timeout, $state, $sessionStorage, notify) {
        var vm = this;
        vm.documentsShow = [];
        // console.log(vm.documents);
        // console.log(vm.panelId);
        // $scope.pdfUrl = 'images/111.pdf';


        vm.$onChanges = function () {
            vm.documentsShow = vm.documents;
            for (var i = 0; i < vm.documentsShow.length; i++) {
                vm.documentsShow[i].createdDate = dateFormat(vm.documentsShow[i].createdDate);

                vm.setVisibilityByCurrentType = function (doc) {
                    console.log(doc);
                    vm.currentType;
                    // = {
                    //     doc : false,
                    //     pdf: false,
                    //     image: false
                    // };
                    if(doc.documentType === 0 || doc.documentType === 1){
                        vm.currentType = 'doc';
                    }
                    else if (doc.documentType === 2) {
                        vm.currentType = 'pdf';
                    }
                    else if (doc.documentType >=3 || doc.documentType <=6 ) {
                        vm.currentType = 'img';
                    }
                    // console.log(vm.fullDocument.documentTypeId);
                    return vm.currentType;
                };
                vm.setVisibilityByCurrentType(vm.documentsShow[i]);
            }
            console.log(vm.documents);
            // console.log(vm.panelId);
        };

        vm.addDocument = function () {



        };

        function dateFormat(dateNotFormated) {
            var date = new Date(dateNotFormated);
            return date.getDate() + '-' + date.getMonth() + '-' + date.getFullYear();
        }

        // vm.openDocument = function (doc) {
        //     console.log(doc);
        //     modalInput(doc);
        // };

        vm.openDocument = function (docId) {
            // docId = 1;
            palletModel.getDocument(docId).then(
                function (success) {
                    vm.objPdf=success.data.data;
                    modalInput(docId);
                    // console.log(success.data.data);
                },
                function (error) {
                    console.log(error);
                }
            );

        };

        function modalInput(docId) {
            var modalInstance = $uibModal.open({
                animation: true,
                component: 'documentDealComponent',
                size: "lg",
                controllerAs: "vm",
                resolve: {
                    docId: function () {
                        return vm.objPdf;
                    }
                }
            });
            modalInstance.result.then(function (newName) {


            }, function () {
            });
        }

        $scope.go = function () {
            console.log('hi');
        };

        vm.fileInfo = {};

        $scope.onLoad = function (e, reader, file, fileList, fileOjects, fileObj) {
            $timeout(function () {
                console.log(vm.fileInfo.base64);

                // var uploadedDocument = {};
                // uploadedDocument.dealPanelId = vm.panelId;
                // uploadedDocument.fileData = vm.fileInfo.base64;
                // uploadedDocument.documentTypeId = getFileExtension(vm.fileInfo.filename);
                // uploadedDocument.documentTitle = title;
                // console.log(uploadedDocument);

                // console.log( getFileExtension(vm.fileInfo.filename));
                vm.showFileName = true;
                // {
                //     userId: 1,
                //     dealPanelId: 1,
                //     fileData: vm.fileInfo.base64
                //     createdDate: 0,
                //     documentTypeId: 1,
                //     documentTitle: "Document1"
                // }

            }, 0);
        };


        vm.addDocu = function () {
            var obg = {
                userId: 1,
                dealPanelId: vm.panelId,
                fileData: "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",
                createdDate: 0,
                documentTypeId: 1,
                documentTitle: "DocumentFromJS"
            };

            var modalInstance = $uibModal.open({
                animation: true,
                component: 'modalComponent',
                size: 'sm',
                resolve: {
                    type: function () {
                        return 'input';
                    }
                }
            });
            modalInstance.result.then(function (newName) {
                if (newName === undefined) {
                    newName = getDefaultName();
                }
                var uploadedDocument = {};
                uploadedDocument.dealPanelId = vm.panelId;
                uploadedDocument.fileData = vm.fileInfo.base64;
                // console.log(uploadedDocument.fileData);
                uploadedDocument.documentTypeId = getDocumentTypeByFileExtension(vm.fileInfo.filename);
                uploadedDocument.documentTitle = newName;
                // console.log(uploadedDocument);
                palletModel.addDocument(uploadedDocument).then(
                    function (success) {
                        // console.log(success.data.data);
                        // vm.documents.push(obg);
                        // console.log(vm.documents.length);
                        // console.log(vm.documents);
                        vm.getDeals(vm.panelId);
                        vm.showFileName = false;
                        notify.set("Document was added", {type: 'success'});
                        // vm.$onChanges();
                    },
                    function (error) {
                        console.log(error);
                    }
                );
            });


        };

        vm.deleteDocument = function (size, type, text, id) {
            var modalInstance = $uibModal.open({
                animation: true,
                component: 'modalComponent',
                size: size,
                resolve: {
                    type: function () {
                        return type;
                    },
                    text: function () {
                        console.log(text);
                        return text;
                    }
                }
            });
            modalInstance.result.then(function () {
                palletModel.deleteDocument(id).then(
                    function (success) {
                        // console.log(vm.documents);
                        vm.getDeals(vm.panelId);
                    },
                    function (error) {
                        // console.log(error);
                    }
                );
            });


        };


        vm.renameDocument = function (id) {
            var modalInstance = $uibModal.open({
                animation: true,
                component: 'modalComponent',
                size: 'sm',
                resolve: {
                    type: function () {
                        return 'input';
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
                        // console.log(vm.documents);
                        vm.getDeals(vm.panelId);
                    },
                    function (error) {
                        // console.log(error);
                    }
                );
            });
        };

        vm.download = function (doc) {
            alert('to do...');
        };



        function getDefaultName() {
            var date = new Date;
            return "Document was created: " + date.toDateString() + " " + date.toLocaleTimeString();
        }

        function getDocumentTypeByFileExtension(filename) {
            var objectTypesExtentions = $sessionStorage.documentTypes;
            var extension = filename.slice((filename.lastIndexOf(".") - 1 >>> 0) + 2);
            if (extension === 'jpeg') {
                extension = 'jpg';
            }
            return Object.keys(objectTypesExtentions)[Object.values(objectTypesExtentions).indexOf(extension.toUpperCase())];
        }

        // $scope.pdfUrl = URL.createObjectURL(currentBlob);

    }

});
