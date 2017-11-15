app.component('questionsComponent', {
    templateUrl: 'partials/questions/questions.component.html',
    controllerAs: 'vm',
    controller: function ($scope,
                          $state,
                          quizModel,
                          notify,
                          $sessionStorage,
                          $filter,
                          browserTitle) {
        var vm = this;
        browserTitle.setBrowserTitleByFilterName('Questions.title');

        vm.questions = $sessionStorage.questions;
        vm.selectedAnswers = $sessionStorage.selectedAnswers;

        function putAnswersToSessionStorage(answers) {
            $sessionStorage.selectedAnswers = answers;
        }

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
        vm.putAnswersToSessionStorage = putAnswersToSessionStorage;
    }
});