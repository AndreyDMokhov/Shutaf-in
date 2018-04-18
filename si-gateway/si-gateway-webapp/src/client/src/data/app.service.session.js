"use strict";
app.factory('sessionService', function ($sessionStorage) {


    function isAuthenticated() {
        var sessionId = $sessionStorage.sessionId;
        return sessionId !== undefined && sessionId !== null && sessionId !== '';
    }

    return {
        isAuthenticated:isAuthenticated
    };

});