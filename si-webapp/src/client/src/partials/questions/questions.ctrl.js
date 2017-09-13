app.controller('QuestionsCtrl', function ($scope, $state, quizFactory, notify,$sessionStorage, $filter) {

    var vm = this;
    vm.isReady = true;

    vm.questions = $sessionStorage.questions;
    vm.answers = $sessionStorage.answers;


    function sendData(send) {
        quizFactory.sendAnswers(send).then(
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

app.directive('quiz', function () {
    return {
        scope: {
            questionsToDirective: '=',
            answersToDirective: '=',
            backData: '&',
            returnAnswer: '='
        },
        restrict: 'AE',
        templateUrl: 'partials/questions/questionTemplate.html',
        link: function (scope, elem, attrs) {

            var questions = scope.questionsToDirective;

            scope.id = 0;
            scope.currentQuestion = questions[scope.id];
            scope.numberOfQuestions = questions.length;
            var answer = [];
            fillAnswerArray();
            scope.currentAnswer = answer[scope.id].answerId;

            scope.nextQuestion = function () {
                answer[scope.id].questionId = scope.currentQuestion.questionId;
                answer[scope.id].answerId = scope.currentAnswer;
                scope.id++;
                scope.currentQuestion = questions[scope.id];
                scope.currentAnswer = answer[scope.id].answerId;
            };

            scope.previousQuestion = function () {
                scope.id--;
                scope.currentQuestion = questions[scope.id];
                scope.currentAnswer = answer[scope.id].answerId;
            };

            scope.save = function () {
                answer[scope.id].questionId = scope.currentQuestion.questionId;
                answer[scope.id].answerId = scope.currentAnswer;
                scope.backData({data: answer});
            };

            function fillAnswerArray() {

                for (var i = 0; i < questions.length; i++) {
                    if (scope.answersToDirective[i]) {
                        answer.push({"questionId": i, "answerId": scope.answersToDirective[i].selectedAnswersIds[0]})
                    }
                    else {
                        answer.push({"questionId": i, "answerId": null})
                    }
                }
            }
        }
    }
});