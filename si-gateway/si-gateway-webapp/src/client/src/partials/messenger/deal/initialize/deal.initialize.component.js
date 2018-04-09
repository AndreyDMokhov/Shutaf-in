app.component('dealInitializeComponent', {
    templateUrl: "partials/messenger/deal/initialize/deal.initialize.component.html",
    bindings: {
        chatInfo: '<'
    },
    controllerAs: "vm",
    controller: function (dealPresentationModel,
                          $uibModal,
                          notify,
                          $filter,
                          $q,
                          accountStatus,
                          $sessionStorage) {

        var vm = this;
        vm.accountStatuses = accountStatus.Statuses;
        var dealWeb = {
            title: '',
            users: []
        };

        vm.currentAccountStatus = ($sessionStorage.accountStatus != null) ? $sessionStorage.accountStatus : 0;

        function collectUsersIdInChat() {
            var deferred = $q.defer();
            dealWeb.users = vm.chatInfo.usersInChat.map(function (x) {
                return x['userId'];
            });

            dealPresentationModel.getAvailableUsers(dealWeb.users).then(
                function (success) {
                    return deferred.resolve(success.data.data);
                },
                function (error) {
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                    return deferred.reject();
                }
            );

            return deferred.promise;
        }

        function configureDeal() {

            collectUsersIdInChat().then(function (dealAvailableUsers) {

                var dealInfo = {};

                dealInfo.users = vm.chatInfo.usersInChat.filter(function (x) {
                    return dealAvailableUsers.availableUsers.indexOf(x['userId']) >= 0;
                });

                if (dealAvailableUsers.activeDeal) {
                    dealInfo.type = 'addUser';
                    dealInfo.dealName = dealAvailableUsers.activeDeal.title;
                } else {
                    dealInfo.type = 'creation';
                }

                var modalInstance = $uibModal.open({

                    animation: true,
                    component: 'dealInitializeModalComponent',
                    size: 'sm',
                    resolve: {

                        dealInfo: function () {
                            return dealInfo;
                        }
                    }
                });
                modalInstance.result.then(function (newName) {

                    if (dealAvailableUsers.activeDeal) {
                        addUserToDeal(dealInfo.users, dealAvailableUsers.activeDeal.dealId);

                    } else {
                        if (newName === undefined) {
                            newName = 'Deal';
                        }
                        dealWeb.title = newName;
                        initializeDeal(dealWeb);
                    }

                });
            });

        }

        function initializeDeal(dealWeb) {
            dealPresentationModel.initiateDeal(dealWeb).then(
                function (success) {
                    notify.set($filter('translate')("Deal.confirmation"));
                },
                function (error) {
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                }
            );
        }

        function addUserToDeal(usersToAdd, dealId) {
            angular.forEach(usersToAdd, function (user) {

                dealPresentationModel.addUsersToDeal(dealId, user.userId).then(
                    function (success) {
                        notify.set('User ' + user.firstName + ' will be added after all deal participants confirm this action');

                    }, function (error) {
                        notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                    }
                );
            });
        }

        vm.configureDeal = configureDeal;
    }
});