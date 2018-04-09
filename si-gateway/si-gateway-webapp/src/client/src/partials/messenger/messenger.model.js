app.factory('messengerModel', function (Restangular, $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/chat');
        RestangularProvider.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
    });


    function getUsers(fullname) {
        if (fullname !== undefined && fullname !== null && fullname !== '') {
            return rest.all('/allUsers?fullname=' + fullname).customGET();
        } else {
            return rest.all('/allUsers').customGET();
        }
    }

    function addChat(chatName, userId) {
        return rest.one('/new/' + chatName + '/' + userId).customGET();
    }

    function getChats() {
        return rest.one('/get/chats').customGET();
    }

    function getAllMessages(chatId) {
        return rest.all('/' + chatId + '/get/messages').customGET();
    }

    function removeUserFromChat(chatId, userId) {
        return rest.one('/' + chatId + '/remove/user/' + userId).customGET();
    }

    function deleteChat(chatId) {
        return rest.one('/' + chatId + '/delete/chat').customGET();
    }

    function addUserToChat(chatId, userId) {
        return rest.one('/' + chatId + '/add/user/' + userId).customGET();
    }

    function updateMessagesAsRead(messagesIdList) {
        return rest.one('/updateMessagesAsRead').customPUT(messagesIdList);
    }
    function renameChat(chatId, chatTitle) {
        return rest.one('/rename/'+ chatId +'/' +chatTitle).customGET();
    }

    return {
        getUsers: getUsers,
        addChat: addChat,
        getChats: getChats,
        getAllMessages: getAllMessages,
        removeUserFromChat: removeUserFromChat,
        addUserToChat: addUserToChat,
        deleteChat: deleteChat,
        updateMessagesAsRead: updateMessagesAsRead,
        renameChat:renameChat
    };
});