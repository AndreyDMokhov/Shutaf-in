app.component('dealInitiateComponent', {
    templateUrl: "partials/deal/dealInitiate/deal.initiate.component.html",
    bindings: {
        chatInfo: '<'
    },
    controllerAs: "vm",
    controller: function (dealPresentationModel) {

        var vm = this;
        // vm.initializeDeal = function () {
        //     console.log(vm.chatInfo);
        // };


        function initializeDeal() {
            console.log("it's work");
            console.log(vm.chatInfo.usersInChat);
            var dealWeb = {
                title: "New deal",
                users: []
            };
            vm.chatInfo.usersInChat.forEach(function (user) {
                dealWeb.users.push(user.userId);
                // console.log(user.userId);
            });
            console.log(dealWeb);
            dealPresentationModel.initiateDeal(dealWeb).then(
                function (success) {
                    console.log(success);
                },
                function (error) {
                    console.log(error);
                }
            );
        }

        vm.initializeDeal = initializeDeal;
    }
});