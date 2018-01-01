"use strict";
app.component('requiredQuestionsComponent', {
    templateUrl: 'partials/questions/requiredQuestions/requiredQuestions.component.html',
    controllerAs: 'vm',
    controller: function ($scope,
                          $state,
                          quizModel,
                          notify,
                          $sessionStorage,
                          $filter,
                          browserTitle) {
        var vm = this;
        browserTitle.setBrowserTitleByFilterName('Questions.requiredQuestions');
        vm.questions = $sessionStorage.questions;
        vm.selectedAnswers = $sessionStorage.selectedAnswers;

        function putAnswersToSessionStorage(answers) {
            $sessionStorage.selectedAnswers = answers;
        }

        function sendData() {
            quizModel.sendAnswers($sessionStorage.selectedAnswers).then(
                function (success) {
                    notify.set($filter('translate')('Questions.confirm'), {type: 'success'});
                    $sessionStorage.accountStatus = 4;
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