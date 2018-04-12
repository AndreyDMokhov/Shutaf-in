app.run(function (Restangular,
                  $state,
                  $filter,
                  notify,
                  $sessionStorage,
                  FILE_MAX_SIZE_MB) {

    Restangular.setErrorInterceptor(
        function (response) {
            if (!response.data || !response.data.error) {

                notify.set($filter('translate')('Error.NoServerResponse'), {type: 'error'});
                return false;
            } else if (response.data.error.errorTypeCode === 'AUT') {

                notify.set($filter('translate')('Error' + '.' + response.data.error.errorTypeCode), {type: 'error'});
                if ($sessionStorage.sessionId !== undefined && $sessionStorage.sessionId !== null && $sessionStorage.sessionId !== '') {

                    $state.go('logout');
                }
                return true;

            } else if (response.data.error.errorTypeCode === 'INP') {

                if (response.data.error.errors.indexOf('INP.image.LimitSize') >= 0 || response.data.error.errors.indexOf('INP.fileData.LimitSize') >= 0) {
                    notify.set($filter('translate')('Deal.panel.file-max-size', {size: FILE_MAX_SIZE_MB / 1024}), {type: 'warn'});
                    return true;
                }
            } else if (response.data.error.errorTypeCode === 'RNF') {

                $state.go("error", {'code': '404'});
                return false;
            } else {

                notify.set($filter('translate')('Error' + '.' + response.data.error.errorTypeCode), {type: 'error'});
                return true;
            }
        }
    );
});