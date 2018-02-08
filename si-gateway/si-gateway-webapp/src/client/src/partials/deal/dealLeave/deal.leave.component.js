app.component('dealLeaveComponent', {
    templateUrl: "partials/deal/dealLeave/deal.leave.component.html",
    bindings: {
        dealInfo: '<'
    },
    controllerAs: "vm",
    controller: function (dealPresentationModel, dealStatus, $state, $filter, notify) {

        var vm = this;
        console.log(vm.dealInfo);
        // vm.initializeDeal = function () {
        //     console.log(vm.chatInfo);
        // };

        function isInDeal() {
            // return Object.keys(vm.dealInfo).length && vm.dealInfo.statusId === dealStatus.Status.ACTIVE;
            return true;
        }

function leaveDeal() {
    console.log(vm.dealInfo);
    console.log(vm.dealInfo.statusId);
    dealPresentationModel.leaveDeal(vm.dealInfo.dealId).then(
        function (success) {
            console.log(success);
            // notify.set($filter('translate')('Registration.request.success'), {type: 'success'});
            notify.set('LEEAAAVEEE'), {type: 'success'};
            // $state.go('deal');
        },
        function (error) {
            console.log(error);
        }
    );

}
        // function initializeDeal() {
        //     console.log("it's work");
        //     console.log(vm.chatInfo.usersInChat);
        //     var dealWeb = {
        //         title: "New deal",
        //         users: []
        //     };
        //     vm.chatInfo.usersInChat.forEach(function (user) {
        //         dealWeb.users.push(user.userId);
        //         // console.log(user.userId);
        //     });
        //     console.log(dealWeb);
        //     dealPresentationModel.initiateDeal(dealWeb).then(
        //         function (success) {
        //             console.log(success);
        //         },
        //         function (error) {
        //             console.log(error);
        //         }
        //     );
        // }

        // vm.initializeDeal = initializeDeal;
        vm.leaveDeal = leaveDeal;
        vm.isInDeal=isInDeal;
    }
});