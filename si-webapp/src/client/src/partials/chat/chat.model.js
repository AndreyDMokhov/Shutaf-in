app.factory('chatModel', function (Restangular, $sessionStorage) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/chat');
        RestangularProvider.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
    });


    function getUsers() {
        return rest.all('/allUsers').customGET();
    }

    function addChat(chatName) {
        return rest.one('/new/' + chatName).customGET();
    }

    function getChats() {
        return rest.one('/get/chats').customGET();
    }

    function getAllMessages(chatId) {
        return rest.all('/' + chatId + '/get/messages').customGET();
    }
    function removeUserFromChat(chatId, userId) {
        return rest.one('/' + chatId + '/remove/user/'+userId).customGET();
    }
    function removeChat(chatId) {
        return rest.one('/' + chatId + '/remove/chat').customGET();
    }
    function getActiveUsersInChat(chatId) {
        return rest.all('/' + chatId + '/get/users').customGET();
    }
    function addUserToChat(chatId, userId){
        return rest.one('/' + chatId + '/add/user/'+userId).customGET();
    }

    return {
        getUsers: getUsers,
        addChat: addChat,
        getChats: getChats,
        getAllMessages: getAllMessages,
        removeUserFromChat: removeUserFromChat,
        getActiveUsersInChat: getActiveUsersInChat,
        addUserToChat: addUserToChat,
        removeChat:removeChat
    }
});