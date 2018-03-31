app.component('dealInitializeComponent', {
    templateUrl: "partials/deal/initialize/deal.initialize.component.html",
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
            dealWeb.users=[];
                vm.chatInfo.usersInChat.forEach(function (user) {
                dealWeb.users.push(user.userId);
            });
        }

        function initializeDeal() {

            getUsersIdInChat();

            var modalInstance = $uibModal.open({

                animation: true,
                component: 'modalWinDealInitialize',
                size: 'sm',
                resolve: {

                    dealInfo: function () {
                        return {users: vm.chatInfo.usersInChat, component: componentType};
                    }
                }
            });
            modalInstance.result.then(function (newName) {
                if (newName === undefined) {
                    newName = namePanelDef;
                }
                dealWeb.title = newName;

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