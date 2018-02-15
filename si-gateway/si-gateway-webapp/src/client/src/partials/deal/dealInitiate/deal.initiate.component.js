app.component('dealInitiateComponent', {
    templateUrl: "partials/deal/dealInitiate/deal.initiate.component.html",
    bindings: {
        chatInfo: '<'
    },
    controllerAs: "vm",
    controller: function (dealPresentationModel, $uibModal, notify, $filter) {

        var vm = this;
        var namePanelDef = 'Deal', componentType = 'deal';
        var dealWeb = {
            title: '',
            users: []
        };

        function getUsersIdInChat() {
            vm.chatInfo.usersInChat.forEach(function (user) {
                dealWeb.users.push(user.userId);
            });
        }

        function initializeDeal() {
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
                dealWeb.title = newName;
                getUsersIdInChat();
                dealPresentationModel.initiateDeal(dealWeb).then(
                    function (success) {
                        notify.set($filter('translate')("Deal.confirmation"));
                    },
                    function (error) {
                        notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                    }
                );

            });
        }

        vm.initializeDeal = initializeDeal;
    }
});