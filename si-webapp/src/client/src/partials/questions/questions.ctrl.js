app.controller('questionsCtrl', function ($scope, $state, quizModel, notify, $sessionStorage, $filter) {

    var vm = this;
    vm.isReady = true;

    function sendData() {
        quizModel.sendAnswers($sessionStorage.selectedAnswers).then(
            function (success) {
                notify.set($filter('translate')('Questions.confirm'), {type: 'success'})
            },
            function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
            }
        );
    }

    vm.sendData = sendData;
});

app.directive('quiz', function ($sessionStorage, $state) {
    return {
        scope: {
            backData: '&',
            returnAnswer: '='
        },
        restrict: 'AE',
        templateUrl: 'partials/questions/questionTemplate.html',
        link: function (scope, elem, attrs) {

            var questions = $sessionStorage.questions;
            scope.id = 0;
            scope.currentQuestion = questions[scope.id];
            scope.numberOfQuestions = questions.length;
            var answers = $sessionStorage.selectedAnswers;
            scope.currentAnswer = $sessionStorage.selectedAnswers[scope.id].answerId;


            scope.nextQuestion = function () {
                answers[scope.id].questionId = scope.currentQuestion.questionId;
                answers[scope.id].answerId = scope.currentAnswer;
                $sessionStorage.selectedAnswers = answers;
                scope.id++;
                scope.currentQuestion = questions[scope.id];
                scope.currentAnswer = answers[scope.id].answerId;
            };

            scope.previousQuestion = function () {
                scope.id--;
                scope.currentQuestion = questions[scope.id];
                scope.currentAnswer = answers[scope.id].answerId;
            };

            scope.save = function () {
                answers[scope.id].questionId = scope.currentQuestion.questionId;
                answers[scope.id].answerId = scope.currentAnswer;
                scope.backData();
                $state.go('userProfile', {id: $sessionStorage.userProfile.userId})
            };
        }
    }
});