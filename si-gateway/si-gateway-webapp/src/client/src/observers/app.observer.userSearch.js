app.factory('userSearchObserver', function () {
    var callbackFiltersObserver = function (){};

    var notifyFilterObserver = function (filters) {
       callbackFiltersObserver(filters);
    };
    var registerFilterObserver = function (callback) {
        callbackFiltersObserver = callback;
    };

    return {
        notifyFilterObserver: notifyFilterObserver,
        registerFilterObserver: registerFilterObserver
    };
});