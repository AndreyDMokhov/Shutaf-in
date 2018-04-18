app.run(function (Restangular,
                  $sessionStorage,
                  sessionService) {
    Restangular.addFullRequestInterceptor(function (element, operation, route, url, headers, params, httpConfig) {
        if (sessionService.isAuthenticated()) {

            headers = {'session_id': $sessionStorage.sessionId};
        }

        return {
            element: element,
            params: params,
            headers: headers,
            httpConfig: httpConfig
        };
    });
});