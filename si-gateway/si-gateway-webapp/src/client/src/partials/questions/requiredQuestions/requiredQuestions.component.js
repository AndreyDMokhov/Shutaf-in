"use strict";
app.component('requiredQuestionsComponent', {
    templateUrl: 'partials/questions/requiredQuestions/requiredQuestions.component.html',
    controllerAs: 'vm',
    controller: function ($scope,
                          $state,
                          quizModel,
                          uiNotification,
                          $sessionStorage,
                          $filter,
                          browserTitleService,
                          initializationService) {
        var vm = this;
        browserTitleService.setBrowserTitleByFilterName('Questions.requiredQuestions');
        vm.questions = $sessionStorage.questions;
        vm.selectedAnswers = $sessionStorage.selectedAnswers;

        function putAnswersToSessionStorage(answers) {
            $sessionStorage.selectedAnswers = answers;

        }

        function sendData() {
            quizModel.sendAnswers($sessionStorage.selectedAnswers).then(
                function (success) {
                    var message = $filter('translate')('Questions.confirm');
                    uiNotification.show(message, 'success');

                    initializationService.initialize(success.data);
                },
                function (error) {}
            );
        }

        vm.sendData = sendData;
        vm.putAnswersToSessionStorage = putAnswersToSessionStorage;
    }
});