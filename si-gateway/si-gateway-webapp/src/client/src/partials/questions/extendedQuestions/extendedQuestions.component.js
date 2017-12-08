app.component('extendedQuestions',{
    templateUrl: 'partials/questions/extendedQuestions/extendedQuestions.component.html',
    controllerAs: 'vm',
    controller: function ($scope,
                          $state,
                          quizModel,
                          notify,
                          $sessionStorage,
                          $filter,
                          browserTitle) {
        var vm =this;
        browserTitle.setBrowserTitleByFilterName('Questions.title');
        vm.questions = $sessionStorage.questionsExtended;
        vm.selectedAnswers = $sessionStorage.selectedExtendedAnswers;
        // console.log(vm.questions);
        // console.log(vm.selectedAnswers);

        function putAnswersToSessionStorage(answers) {
            $sessionStorage.selectedExtendedAnswers = answers;
        }
        //
        function sendData() {
        //     quizModel.sendAnswers($sessionStorage.selectedAnswers).then(
        //         function (success) {
        //             notify.set($filter('translate')('Questions.confirm'), {type: 'success'});
        //         },
        //         function (error) {
        //             notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
        //         }
        //     );
            return 'hi';
        }
        //
        vm.sendData = sendData;
        vm.putAnswersToSessionStorage = putAnswersToSessionStorage;
    }
});