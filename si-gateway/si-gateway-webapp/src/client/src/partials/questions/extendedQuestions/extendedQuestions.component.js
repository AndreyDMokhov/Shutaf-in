"use strict";
app.component('extendedQuestionsComponent', {
    templateUrl: 'partials/questions/extendedQuestions/extendedQuestions.component.html',
    controllerAs: 'vm',
    controller: function ($scope,
                          $state,
                          extendedQuestionsModel,
                          notify,
                          $sessionStorage,
                          $filter,
                          browserTitle) {
        var vm = this;
        browserTitle.setBrowserTitleByFilterName('Questions.extendedQuestions');
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
                    notify.set($filter('translate')('Questions.confirm'), {type: 'success'});
                },
                function (error) {}
            );

        }

        vm.sendData = sendData;
        vm.putAnswersToSessionStorage = putAnswersToSessionStorage;
    }
});