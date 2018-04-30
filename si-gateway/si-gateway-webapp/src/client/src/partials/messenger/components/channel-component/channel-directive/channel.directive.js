app.directive('channelDirective', function (messengerModel, webSocketService, $sessionStorage,
                                            ngDialog, messengerCurrentDataService, messengerManagementService,
                                            messengerChannelService) {
    return {
        restrict: "E",
        scope: {
            chatData: '='
        },
        templateUrl: 'partials/messenger/components/channel-component/channel-directive/channel.html',

        link: function (scope, element, attrs) {

            scope.messages = [];
            scope.lastMessage = {};
            scope.characterLimit = 15;
            scope.newMessageCounter = 0;
            var userId = $sessionStorage.userProfile.userId;
            var messageIdList = [];
            var chatElement = angular.element(element[0].children[0]);

            //TODO: move the whole messaging logic to service

            function init() {
                if (!messengerChannelService.isSubscribed()) {
                    setTimeout(function () {
                        init();
                    }, 50);
                }
                else {
                    messengerChannelService.registerChannelActivateObserver(activateChannel, scope.chatData);
                    messengerChannelService.registerSubscriptionCallback(addChatMessage, scope.chatData);
                }
            }

            function activateChannel(chatData) {
                    scope.chatData = chatData;
                    updateMessagesAsRead();
                    messengerCurrentDataService.setMessages(scope.messages);
                    messengerChannelService.registerSubscriptionCallback(addChatMessage, scope.chatData);
            }

            function addChatMessage(message) {
                markMessageNotRead(message);
                scope.messages.push(message);
                scope.lastMessage = message;
                if (messengerCurrentDataService.currentChat.id === scope.chatData.id) {
                    updateMessagesAsRead();
                    messengerCurrentDataService.setMessages(scope.messages);
                }
            }

            function getAllMessages() {
                messengerModel.getAllMessages(scope.chatData.id).then(
                    function (success) {
                        scope.messages = success.data.data;
                        scope.lastMessage = scope.messages[scope.messages.length - 1];
                        findNewMessages();
                    });
            }

            scope.enterChat = function () {
                updateMessagesAsRead();
                messengerCurrentDataService.setMessages(scope.messages);
                messengerCurrentDataService.setCurrentChat(scope.chatData);
            };

            function findNewMessages() {
                for (var i = 0; i < scope.messages.length; i++) {
                    if (scope.messages[i].usersToNotify.length !== 0) {
                        markMessageNotRead(scope.messages[i]);
                    }
                }
            }

            function markMessageNotRead(message) {
                if (message.usersToNotify.includes(userId)) {
                    chatElement[0].style.backgroundColor = '#E1E1E1';
                    message.isNew = true;
                    scope.newMessageCounter++;
                    messageIdList.push(message.messageId);
                }
                else {
                    message.isNew = false;
                }
            }

            function updateMessagesAsRead() {
                if (Object.keys(messageIdList).length === 0) {
                    return;
                }
                var messageIdWrapper = {};
                messageIdWrapper.messageIdList = messageIdList;
                messengerModel.updateMessagesAsRead(messageIdWrapper);
                scope.newMessageCounter = 0;
                chatElement[0].style.backgroundColor = '#FAFAFA';
                messageIdList = [];
            }

            scope.deleteChat = function () {
                messengerManagementService.deleteChat(scope.chatData);
            };

            scope.editChatTitle = function () {
                scope.dialog = ngDialog.open({
                    template: 'partials/messenger/components/channel-component/channel-directive/chat-rename.popup.html',
                    scope: scope,
                    showClose: false,
                    className: 'ngdialog-theme-default',
                    closeByDocument: true
                });
            };

            scope.setNewChatTitle = function (newChatTitle) {
                scope.dialog.close();
                if (newChatTitle) {
                    messengerManagementService.renameChat(scope.chatData, newChatTitle);
                }
            };

            getAllMessages();
            init();
        }
    };
});