"use strict";
app.component('extendedQuestionsComponent', {
    templateUrl: 'partials/questions/extendedQuestions/extendedQuestions.component.html',
    controllerAs: 'vm',
    controller: function ($scope,
                          $state,
                          extendedQuestionsModel,
                          uiNotification,
                          $sessionStorage,
                          $filter,
                          browserTitleService) {
        var vm = this;
        browserTitleService.setBrowserTitleByFilterName('Questions.extendedQuestions');
        vm.questions = $sessionStorage.questionsExtended;
        vm.selectedAnswers = $sessionStorage.selectedExtendedAnswers;
        var answersToSend = [];

        function putAnswersToSessionStorage(answers) {
            $sessionStorage.selectedExtendedAnswers = answers;

            for (var i = 0; i < answers.length; i++) {
                if (answers[i].questionImportanceId != null) {
                    answersToSend.push(answers[i]);
                }
            }
        }

        function sendData() {
            extendedQuestionsModel.sendAnswers(answersToSend).then(
                function (success) {

                    var message = $filter('translate')('Questions.confirm');
                    uiNotification.show(message, 'success');
                },
                function (error) {}
            );

        }

        vm.sendData = sendData;
        vm.putAnswersToSessionStorage = putAnswersToSessionStorage;
    }
});