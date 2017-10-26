app.directive('chatRoom', function (chatModel, webSocketService, $sessionStorage, ngDialog) {
    return {
        restrict: "E",
        scope: {
            currentChatId: '=',
            chatData: '=',
            updateChatData: '&',
            updateChatMessages: '&',
            removeChat: '&',
            renameChat: '&'
        },
        templateUrl: 'partials/chat/components/chat-room-item/chat-room.html',

        link: function (scope, element, attrs) {

            var destination = '/api/subscribe/chat/' + scope.chatData.id;
            scope.messages = [];
            scope.lastMessage = {};
            scope.characterLimit = 20;
            scope.newMessageCounter = 0;
            var userId = $sessionStorage.userProfile.userId;
            var messageIdList = [];
            var body = angular.element(element[0].children[0]);

            function initWsConnection() {
                if (!webSocketService.isConnectionReady()) {
                    setTimeout(function () {
                        initWsConnection();
                    }, 1000);
                }
                else {
                    _doSubscribe(destination);
                }
            }

            function _doSubscribe(destination) {
                webSocketService.subscribe(destination).then(null, null,
                    function (message) {
                        addChatMessage(message);
                    });
                /**
                 *  Should be called after getting status: connection is ready,
                 *  in other way we will get multiple subscriptions
                 */
                webSocketService.registerObserverCallback(initWsConnection);
            }

            function addChatMessage(message) {
                markMessageNotRead(message);
                scope.messages.push(message);
                scope.lastMessage = message;
                if (scope.currentChatId.id === scope.chatData.id) {
                    scope.updateChatMessages({messages: scope.messages});
                    updateMessagesAsRead();
                }
            }

            function getAllMessages() {
                chatModel.getAllMessages(scope.chatData.id).then(
                    function (success) {
                        scope.messages = success.data.data;
                        scope.lastMessage = scope.messages[scope.messages.length - 1];
                        findNewMessages();
                    });
            }

            scope.enterChat = function () {
                updateMessagesAsRead();
                scope.updateChatData({chatData: scope.chatData});
                scope.updateChatMessages({messages: scope.messages});
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
                    body[0].style.backgroundColor = '#E1E1E1';
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
                chatModel.updateMessagesAsRead(messageIdWrapper);
                scope.newMessageCounter = 0;
                body[0].style.backgroundColor = '#FAFAFA';
                messageIdList = [];
            }

            scope.deleteChat = function () {
                scope.messages = [];
                scope.lastMessage = {};
                scope.removeChat();
            };

            scope.editChatTitle = function () {
                scope.dialog = ngDialog.open({
                    template: 'partials/chat/components/chat-room-item/chat-rename.popup.html',
                    scope: scope,
                    showClose: false,
                    className: 'ngdialog-theme-default',
                    closeByDocument: true
                });
            };

            scope.setNewChatTitle = function (newChatTitle) {
                scope.dialog.close();
                if (newChatTitle) {
                    scope.renameChat({chatId: scope.chatData.id, chatTitle: newChatTitle});
                }
            };

            getAllMessages();
            initWsConnection();
        }
    };
});