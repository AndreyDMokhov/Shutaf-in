/**
 * Created by evgeny on 7/13/2017.
 */
"use strict";
app.controller('errorsController', function (
                                            $filter,
                                            $stateParams,
                                            browserTitle) {

    var vm = this;
    vm.errorData = {};

    function showError() {
        var code = $stateParams.code;
        if (code === undefined || code === null || code === "") {
            code = 404;
        }
        vm.errorData.code = code;
        vm.errorData.description = $filter('translate')('Error.RNF');
        browserTitle.setBrowserTitleByFilterName(null);
    }

    showError();

});