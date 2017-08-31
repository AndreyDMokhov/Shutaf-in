app.factory('quizFactory', function (Restangular, $q, $sessionStorage) {
    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/user/match');
        RestangularProvider.setDefaultHeaders({'session_id': $sessionStorage.sessionId});
    });
    var data = {};

    //10 000 SHEKEL
    function get123() {
        // return rest.one('/template').customGET();

        //Сделка. Нужно подождать, я вернусь с ответом
        //$q.defer()
        var deferred = $q.defer();
        data = rest.one("/template").withHttpConfig({timeout: 10000});

        data.get().then(
            function (success) {
                data.questionAnswers = success.data.data;
                //deferred.resolve -> конверт
                //data.questionAnswers -> деньги
                return deferred.resolve(data.questionAnswers); // -> конверт с деньгами внутри
            },
            function (error) {
                return deferred.reject(); // нет денег
            }
        );
        return deferred.promise; // -> Я обещаю, что я вернусь!!


    }

    return {

        get123: get123


    };
});