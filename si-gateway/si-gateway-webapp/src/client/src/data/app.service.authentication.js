"use strict";
app.factory('authenticationService', function ($sessionStorage, authenticationObserver) {


    function isAuthenticated() {
        var sessionId = $sessionStorage.sessionId;
        return sessionId !== undefined && sessionId !== null && sessionId !== '';
    }

    function getSessionId() {
        return {'session_id': isAuthenticated() ? $sessionStorage.sessionId : null};
    }

    function setSessionId(sessionId) {
        $sessionStorage.sessionId = sessionId;
        authenticationObserver.notifyServiceObservers();
    }

    function removeSessionId() {
        delete $sessionStorage.sessionId;
        authenticationObserver.notifyServiceObservers();
    }

    return {
        isAuthenticated: isAuthenticated,
        getSessionId: getSessionId,
        setSessionId: setSessionId,
        removeSessionId: removeSessionId
    };

});