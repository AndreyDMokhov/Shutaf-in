app.run(function (Restangular,
                  $state,
                  $filter,
                  uiNotification,
                  authenticationService,
                  FILE_MAX_SIZE_MB) {

    Restangular.setErrorInterceptor(
        function (response) {
            var message = '';
            if (!response.data || !response.data.error) {

                message = $filter('translate')('Error.NoServerResponse');
                uiNotification.show(message, 'error');

                return false;
            } else if (response.data.error.errorTypeCode === 'AUT') {

                message = $filter('translate')('Error' + '.' + response.data.error.errorTypeCode);
                uiNotification.show(message, 'error');


                if (authenticationService.isAuthenticated()) {

                    $state.go('logout');
                }
                return true;

            } else if (response.data.error.errorTypeCode === 'INP') {

                if (response.data.error.errors.indexOf('INP.image.LimitSize') >= 0 || response.data.error.errors.indexOf('INP.fileData.LimitSize') >= 0) {
                    message = $filter('translate')('Deal.panel.file-max-size', {size: FILE_MAX_SIZE_MB / 1024});

                    uiNotification.show(message, 'warn');

                    return true;
                }
            } else if (response.data.error.errorTypeCode === 'RNF') {

                $state.go("error", {'code': '404'});
                return false;
            } else {

                message = $filter('translate')('Error' + '.' + response.data.error.errorTypeCode);
                uiNotification.show(message, 'error');

                return true;
            }
        }
    );
});