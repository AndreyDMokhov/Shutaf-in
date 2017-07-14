/**
 * Created by evgeny on 7/13/2017.
 */
app.controller('errorsController', function ($filter, $scope, $stateParams) {

    function showError() {
        var code = $stateParams.code;
        if (code===undefined || code===null || code===""){
            code = 404;
        }
        $scope.errorCode = $filter('translate')('Error.'+code+'.code');
        $scope.errorDescription = $filter('translate')('Error.'+code+'.description');
    }

    showError();

});