app.component('usersDealComponent', {
    templateUrl: "partials/deal/component/users/users.component.html",
    bindings: {
        dealInfo: '<'

    },
    controllerAs: 'vm',
    controller: function (dealModel,
                          uiNotification,
                          $filter,
                          $uibModal,
                          $sessionStorage) {
        var vm = this;
        var limitUsersForShow = 4;
        var lastElement = 0;
        vm.partUsersInfo = [];
        vm.arrowUp = false;
        vm.arrowDown = false;
        vm.arrows = false;
        vm.userID = $sessionStorage.userProfile.userId;

        vm.$onInit = function () {
            var i;
            if (vm.dealInfo.users.length <= limitUsersForShow) {
                vm.partUsersInfo = vm.dealInfo.users;
            } else {
                for (i = 0; i < limitUsersForShow; i++) {
                    vm.partUsersInfo[i] = vm.dealInfo.users[i];
                }
                vm.arrows = true;
                vm.arrowDown = true;
                lastElement = i;
            }
        };

        vm.downArrow = function () {

            if (lastElement === vm.dealInfo.users.length) {
                return;
            }
            var i = lastElement;
            var j = 0;
            lastElement = lastElement + limitUsersForShow;
            vm.partUsersInfo = [];
            for (; i < lastElement && i < vm.dealInfo.users.length; i++) {
                vm.partUsersInfo[j++] = vm.dealInfo.users[i];

            }

            if (i == vm.dealInfo.users.length) {
                vm.arrowDown = false;
            }
            lastElement = i;
            vm.arrowUp = true;

        };

        vm.upArrow = function () {

            var j = 0;

            var previousElement = lastElement - limitUsersForShow - 1;

            if (previousElement < 0) {
                previousElement = 0;
            }

            for (var i = previousElement; i < lastElement - 1; i++) {
                vm.partUsersInfo[j++] = vm.dealInfo.users[i];
            }

            if (previousElement === 0) vm.arrowUp = false;
            vm.arrowDown = true;
            lastElement = limitUsersForShow;
        };

        function getUserImage(img) {
            if (!img) {
                return '../../images/default_avatar.png';
            }
            else {
                return 'data:image/jpeg;base64,' + img;
            }
        }

        vm.remove = function (user) {

            if (user.userId === vm.userID) {
                uiNotification.show($filter('translate')('Error.DSR'), 'warn');
                return;
            }

            var type = 'removeUser';
            var modalInstance = $uibModal.open({

                animation: true,
                component: 'modalComponent',
                size: 'sm',
                resolve: {

                    type: function () {
                        return {type: type, firstName: user.firstName.toUpperCase(), lastName: user.lastName.toUpperCase()};
                    }
                }
            });
            modalInstance.result.then(function () {

                dealModel.removeUser(vm.dealInfo.dealId, user.userId).then(
                    function (success) {
                        uiNotification.show($filter('translate')('Deal.request.removeUser', {
                            firstName: user.firstName.toUpperCase(),
                            lastName: user.lastName.toUpperCase(),
                            dealName: vm.dealInfo.title}));
                    },
                    function (error) {}
                );

            });


        };

        vm.getUserImage = getUserImage;
    }
});