app.component('dealComponent', {
    templateUrl: 'partials/deal/deal.component/deal.component.html',
    bindings: {
        dealInfo: '<'
    },
    controllerAs: 'vm',
    controller: function (dealModel, $uibModal, $sessionStorage, $filter) {

        var vm = this;
        var namePanelDef = "Pallet";
        var ACTIVE = 2;
        vm.panelId;
        vm.documents = [];
        vm.panels = {};

        vm.isDealStatusActive = function () {
            return vm.statusDeal === ACTIVE;
        };

        vm.$onChanges = function () {
            if (Object.getOwnPropertyNames(vm.dealInfo.panels).length === 0) {
                vm.statusDeal = vm.dealInfo.statusId;
                vm.emptyDeal = true;
            }
            else {
                vm.emptyDeal = false;
                vm.statusDeal = vm.dealInfo.statusId;
                vm.panels = vm.dealInfo.panels;
                vm.panelId = vm.dealInfo.firstPanel.panelId;
                vm.documents = vm.dealInfo.firstPanel.documents;
                vm.palletClicked = true;
            }
        };

        vm.selectPanel = function (id) {
            dealModel.getPanel(id).then(
                function (success) {
                    var panel = success.data.data;
                    vm.documents = panel.documents;
                    vm.panelId = id;
                },
                function (error) {
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                });
        };

        vm.addPallet = function (dealId, size, type) {
            var modalInstance = $uibModal.open({
                animation: true,
                component: 'modalComponent',
                size: size,
                resolve: {
                    type: function () {
                        return type;
                    }
                }
            });
            modalInstance.result.then(function (newName) {
                if (newName === undefined) {
                    newName = namePanelDef;
                }
                var params = {dealId: dealId, title: newName};
                dealModel.addPanel(params).then(
                    function (success) {
                        vm.emptyDeal = false;
                        vm.palletClicked = true;
                        var panel = success.data.data;
                        vm.panelId = panel.panelId;
                        vm.panels[panel.panelId] = panel.title;
                        vm.showLoading = false;
                    },
                    function (error) {
                        notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                    }
                );

            });
        };

        vm.renamePanel = function (size, type, idPanel) {
            var modalInstance = $uibModal.open({
                animation: true,
                component: 'modalComponent',
                size: size,
                resolve: {
                    type: function () {
                        return type;
                    }
                }
            });
            modalInstance.result.then(
                function (newName) {
                    if (newName === undefined) {
                        newName = namePanelDef;
                    }
                    var param = {title: newName};
                    dealModel.renamePanel(idPanel, param).then(
                        function (success) {
                            var panel = success.data.data;
                            vm.panels[panel.panelId] = panel.title;
                        },
                        function (error) {
                            notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                        });
                },
                function (error) {
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                });
        };

        vm.removePanel = function (size, type, text, idPanel) {
            var modalInstance = $uibModal.open({
                animation: true,
                component: 'modalComponent',
                size: size,
                resolve: {
                    type: function () {
                        return type;
                    }
                }
            });
            modalInstance.result.then(function () {
                var param = {
                    userId: $sessionStorage.userProfile.userId,
                    panelId: idPanel
                };
                dealModel.removePanel(param).then(function (success) {
                    delete vm.panels[idPanel];
                });
            });
        };
    }
});

app.component('modalComponent',
    {
        templateUrl: 'ModalContent.html',
        bindings: {
            close: '&',
            dismiss: '&',
            resolve: '<'
        },
        controllerAs: "vm",
        controller: function () {
            var vm = this;

            vm.ok = function () {

                vm.close({$value: vm.newTabName});
            };

            vm.cancel = function () {

                vm.dismiss({$value: 'cancel'});
            };

        }

    });