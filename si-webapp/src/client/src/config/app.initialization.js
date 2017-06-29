/**
 * Created by evgeny on 6/26/2017.
 */
app.factory('initializationService', function (Restangular) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api');
    });

    function getLanguages() {
        return rest.one('/initialization/languages').get().then(
            function(result){//success
                var res = {};
                res.languages = result.data.languages;
                return res;
            },
            function(err){//fail
                console.log(err);
                return err;
            }
        );
    }

    function getAll() {
        return rest.one('/initialization/').get().then(
            function(result){//success
                var res = {};
                res.languages = result.data.languages;
//                res.gender = {};
                return res;
            },
            function(err){//fail
                console.log(res);
                return {};
            }
        );
    }

    return {
        getLanguages:getLanguages,
        getAll:getAll
    }
});