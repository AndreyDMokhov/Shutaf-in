app.component('dealComponent', {
    templateUrl: 'partials/deal/component/deal.component.html',
    bindings: {
        dealInfo: '<'
    },
    controllerAs: 'vm',
    controller: function (
        dealModel,
        $uibModal,
        $sessionStorage,
        $filter,
        notify,
        dealStatus) {

        var vm = this;
        var namePanelDef = "Panel";
        vm.panelId;
        vm.documents = [];
        vm.panels = {};
        var componentType = 'folder';

        vm.isDealStatusActive = function () {
            return vm.statusDeal === dealStatus.Status.ACTIVE;
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
                vm.panelClicked = true;
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

        vm.addPanel = function (dealId) {
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
            modalInstance.result.then(function (newName) {
                if (newName === undefined) {
                    newName = namePanelDef;
                }
                var params = {dealId: dealId, title: newName};
                dealModel.addPanel(params).then(
                    function (success) {
                        vm.emptyDeal = false;
                        vm.panelClicked = true;
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

        vm.renamePanel = function (idPanel) {
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
                });
        };

        vm.removePanel = function (idPanel) {
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
                var param = {
                    userId: $sessionStorage.userProfile.userId,
                    panelId: idPanel
                };
                dealModel.removePanel(param).then(
                    function (success) {
                        delete vm.panels[idPanel];
                    }, function (error) {
                        notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                    });
            });
        };
    }
});
