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
        browserTitle.setBrowserTitleByFilterName('Questions.title');
        vm.questions = $sessionStorage.questionsExtended;
        vm.selectedAnswers = $sessionStorage.selectedExtendedAnswers;
        var answersToSend = [];

        function putAnswersToSessionStorage(answers) {
            $sessionStorage.selectedExtendedAnswers = answers;

            for (var i = 0; i < answers.length; i++) {
                if (answers[i].questionImportanceId != null){
                    answersToSend.push(answers[i]);}
            }
        }

        function sendData() {
            extendedQuestionsModel.sendAnswers(answersToSend).then(
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