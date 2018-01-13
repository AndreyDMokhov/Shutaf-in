/**
 * Created by evgeny on 1/1/2018.
 */

app.service('notifySessionStorageChangeService', function() {

    var vm = this;
    vm.serviceCallbacks = [];

    function registerServiceObserver(callback) {
        vm.serviceCallbacks.push(callback);
    }

    function notifyServiceObservers() {
        if (vm.serviceCallbacks !== undefined){
            angular.forEach(vm.serviceCallbacks, function (callback) {
                callback();
            });
        }
    }

    vm.registerServiceObserver = registerServiceObserver;
    vm.notifyServiceObservers = notifyServiceObservers;

});
