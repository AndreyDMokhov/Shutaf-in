/**
 * Contains channel component, message list and send form directives, and users directives (from matching and current chat users)
 */
app.component('messengerUiComponent', {
    templateUrl: 'partials/messenger/messenger-ui.component.html',
    bindings: {},
    controllerAs: 'vm',

    controller: function (messengerModel, $sessionStorage, messengerChannelService, messengerCurrentDataService, messengerManagementService) {

        var vm = this;

        vm.users = [];
        vm.currentChat = {};
        vm.characterLimit = 15;

        function activate() {
            if (!messengerChannelService.listOfChatsCallbacks) {
                setTimeout(function () {
                    activate();
                }, 50);
            }
            else {
                messengerCurrentDataService.registerCurrentChatObserver(updateCurrentChat);
                getUserData();
                angular.element(document).ready(function () {
                    messengerManagementService.activateMessenger();
                });
            }
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

        activate();

        vm.getUserData = getUserData;
    }
});