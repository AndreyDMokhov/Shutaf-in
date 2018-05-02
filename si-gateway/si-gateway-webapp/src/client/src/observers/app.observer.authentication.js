app.service('authenticationObserver', function() {

    var vm = this;
    vm.serviceCallbacks = [];

    function registerObserverCallback(callback) {
        vm.serviceCallbacks.push(callback);
    }

    function notifyServiceObservers() {
        if (vm.serviceCallbacks !== undefined){
            angular.forEach(vm.serviceCallbacks, function (callback) {
                callback();
            });
        }
    }

    vm.registerObserverCallback = registerObserverCallback;
    vm.notifyServiceObservers = notifyServiceObservers;

});
