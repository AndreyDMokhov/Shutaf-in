/**
 * Contains channel component, message list and send form directives, and users directives (from matching and current chat users)
 */
app.component('messengerUiComponent', {
    templateUrl: 'partials/messenger/messenger-ui.component.html',
    bindings: {},
    controllerAs: 'vm',

    controller: function (messengerModel,
                          $sessionStorage,
                          messengerChannelService,
                          messengerCurrentDataService) {

        var vm = this;

        vm.users = [];
        vm.currentChat = {};
        vm.characterLimit = 15;

        function activate() {
            messengerCurrentDataService.registerCurrentChatObserver(updateCurrentChat);
            getUserData();
        }

        function updateCurrentChat() {
            vm.currentChat = messengerCurrentDataService.currentChat;
        }

        function getUserData() {
            messengerModel.getUsers().then(
                function (success) {
                    vm.users = success.data.data;
                });
        }


        function initializeDeal() {
            console.log("it's work");
            console.log(vm.currentChat);
        }

        function isInChatGroup() {
            return Object.keys(vm.currentChat).length;
        }

        activate();

        vm.getUserData = getUserData;
        vm.initializeDeal = initializeDeal;
        vm.isInChatGroup = isInChatGroup;
    }
});