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
                          browserTitle,
                          initializationService) {
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
                    initializationService.initialize(success.data);
                },
                function (error) {}
            );
        }

        vm.sendData = sendData;
        vm.putAnswersToSessionStorage = putAnswersToSessionStorage;
    }
});