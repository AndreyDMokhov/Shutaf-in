app.controller('questionsCtrl', function ($scope,
                                          $state,
                                          quizModel,
                                          notify,
                                          $sessionStorage,
                                          $filter,
                                          browserTitle) {

    browserTitle.setBrowserTitleByFilterName('Questions.title');
    var vm = this;

    vm.answers = $sessionStorage.selectedAnswers;


    function sendData() {
        quizModel.sendAnswers($sessionStorage.selectedAnswers).then(
            function (success) {
                notify.set($filter('translate')('Questions.confirm'), {type: 'success'});
            },
            function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
            }
        );
    }

    vm.sendData = sendData;
});