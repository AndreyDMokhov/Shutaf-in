app.component('dealComponent', {
    templateUrl: 'partials/deal/deal.component/deal.component.html',
    bindings: {
        dealInfo: '<'

    },
    controllerAs: 'vm',
    controller: function (dealModel, $uibModal, $sessionStorage) {

        var vm = this;
        var namePanelDef = "Pallet";
        var ACTIVE = 2;                   //?????????????????/
        vm.panelId;
        vm.documents = [];

        vm.isDealStatusActive = function () {

            return vm.statusDeal === ACTIVE;
        };
        vm.$onInit = function () {
            vm.statusDeal = vm.dealInfo.statusId;
            vm.panels = vm.dealInfo.panels;
            vm.panelId = vm.dealInfo.firstPanel.panelId ;
            vm.documents = vm.dealInfo.firstPanel.documents;
            vm.palletClicked = true;
            // console.log(vm.panels);
        };


        vm.selectPanel = function (id) {
            // vm.showLoading = true;
            dealModel.getPanel(id).then(function (success) {
                var panel = success.data.data;
                vm.documents = panel.documents;
                vm.panelId = id;
                // vm.showLoading = false;
                console.log(panel);
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
                dealModel.addPanel(params).then(function (success) {
                    var panel = success.data.data;
                    vm.panels[panel.panelId] = panel.title;
                });

            });
        };

        vm.renamePanel = function (size, type, idPanel) {
            console.log(idPanel);
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
                var param = {title: newName};
                dealModel.renamePanel(idPanel, param).then(function (success) {
                    var panel = success.data.data;
                    vm.panels[panel.panelId] = panel.title;
                });
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
                    },
                    text: function () {
                        console.log(text);
                        return text;
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

app.component('modalComponent', {             // param = {
    //         type:  type of window ('input' or 'text')
    //         text:  if the type of window is 'text', the key for the text
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