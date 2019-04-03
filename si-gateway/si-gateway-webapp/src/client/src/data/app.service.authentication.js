"use strict";
app.factory('authenticationService', function ($rootScope, $sessionStorage, authenticationObserver) {

    var vm = this;
    function watchLoginChange() {
debugger;
        FB.Event.subscribe('auth.authResponseChange', function (res) {

            if (res.status === 'connected') {

                /*
                 The user is already logged,
                 is possible retrieve his personal info
                */
                getUserInfo();

                /*
                 This is also the point where you should create a
                 session for the current user.
                 For this purpose you can use the data inside the
                 res.authResponse object.
                */

            }
            else {

                /*
                 The user is not logged to the app, or into Facebook:
                 destroy the session on the server.
                */

            }

        });
    }

        function getUserInfo() {

            var _self = this;

            FB.api('/me', function (res) {
                $rootScope.$apply(function () {
                    console.log(res);
                    $rootScope.user = _self.user = res;

                });
            });

        }

        function isAuthenticated() {
            debugger;
            console.log(typeof(FB));
            if(typeof(FB) === 'object') {
                FB.getLoginStatus(function(response) {
                    console.log(response);
                    // statusChangeCallback(response);
                });
            }

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
            removeSessionId: removeSessionId,
            watchLoginChange: watchLoginChange,
            getUserInfo: getUserInfo

        };

    }

);