app.component('dealInitializeModalComponent',
    {
        templateUrl: 'partials/messenger/deal/initialize/modal/deal.initialize.modal.html',
        bindings: {
            close: '&',
            dismiss: '&',
            resolve: '<'
        },
        controllerAs: "vm",
        controller: function ($filter) {
            var vm = this;
            var translatedComponent = $filter('translate')("Deal." + vm.resolve.dealInfo.component);

            var limitUsersForShow = 3;
            var lastElement = 0;
            vm.partUsersInfo = [];
            vm.arrowPreview = false;
            vm.arrowNext = false;
            vm.arrows = false;


            vm.$onInit = function () {
                var i;
                if (vm.resolve.dealInfo.users.length <= limitUsersForShow) {
                    vm.partUsersInfo = vm.resolve.dealInfo.users;
                } else {
                    for (i = 0; i < limitUsersForShow; i++) {
                        vm.partUsersInfo[i] = vm.resolve.dealInfo.users[i];
                    }
                    vm.arrows = true;
                    vm.arrowNext = true;
                    lastElement = i;
                }
            };

            vm.next = function () {

                if (lastElement === vm.resolve.dealInfo.users.length) {
                    return;
                }
                var i = lastElement;
                var j = 0;
                lastElement = lastElement + limitUsersForShow;
                vm.partUsersInfo = [];
                for (; i < lastElement && i < vm.resolve.dealInfo.users.length; i++) {
                    vm.partUsersInfo[j++] = vm.resolve.dealInfo.users[i];

                }

                if (i === vm.resolve.dealInfo.users.length) {
                    vm.arrowNext = false;
                }
                lastElement = i;
                vm.arrowPreview = true;

            };

            vm.preview= function () {

                var j = 0;

                var previousElement = lastElement - limitUsersForShow - 1;

                if (previousElement < 0) {
                    previousElement = 0;
                }

                for (var i = previousElement; i < lastElement - 1; i++) {
                    vm.partUsersInfo[j++] = vm.resolve.dealInfo.users[i];
                }

                if (previousElement === 0) vm.arrowPreview = false;
                vm.arrowNext = true;
                lastElement = limitUsersForShow;
            };

            vm.componentData = {
                component: translatedComponent
            };
            vm.ok = function () {
                vm.close({$value: vm.newTabName});
            };
            vm.cancel = function () {
                vm.dismiss({$value: 'cancel'});
            };
        }

    });