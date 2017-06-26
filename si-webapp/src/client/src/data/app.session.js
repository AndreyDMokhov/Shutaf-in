app.factory('sessionService', function () {


    function isAuthenticated() {
        var sessionId = localStorage.getItem('session_id');
        return sessionId !== undefined && sessionId !== null && sessionId !== '';
    }

    return {
        isAuthenticated:isAuthenticated
    }

});