app.run(function (Restangular,
                  $sessionStorage,
                  authenticationService,
                  authenticationObserver) {

    var sessionId = {};

    function getSessionId(){
        sessionId = authenticationService.getSessionId();
    }

    authenticationObserver.registerObserverCallback(getSessionId);

    Restangular.addFullRequestInterceptor(function (element, operation, route, url, headers, params, httpConfig) {
        if (authenticationService.isAuthenticated()) {
            headers = sessionId;
        }

        return {
            element: element,
            params: params,
            headers: headers,
            httpConfig: httpConfig
        };
    });
});