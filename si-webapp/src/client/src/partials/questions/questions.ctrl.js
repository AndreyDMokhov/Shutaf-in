app.controller('questionsCtrl', function ($scope, $state, quizModel, notify, $sessionStorage, $filter) {

    var vm = this;
    vm.isReady = true;

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
            scope.currentPage = 1;

            scope.currentQuestion = questions[scope.currentPage - 1];
            scope.numberOfQuestions = questions.length;
            var answers = $sessionStorage.selectedAnswers;
            scope.currentAnswer = findById(answers, scope.currentQuestion.questionId);
            var currId = scope.currentPage;
            scope.pageChanged = function () {
                answers[currId - 1].questionId = scope.currentQuestion.questionId;
                answers[currId - 1].answerId = scope.currentAnswer;
                currId = scope.currentPage;
                $sessionStorage.selectedAnswers = answers;
                scope.currentQuestion = questions[currId - 1];
                scope.currentAnswer = findById(answers, scope.currentQuestion.questionId);
            };

            scope.save = function () {
                answers[currId - 1].questionId = scope.currentQuestion.questionId;
                answers[currId - 1].answerId = scope.currentAnswer;
                $sessionStorage.selectedAnswers = answers;

                scope.backData();
                $state.go('userProfile', {id: $sessionStorage.userProfile.userId});
            };
            scope.dis = false;

            scope.totalItems = scope.numberOfQuestions;
            scope.itemsPerPage = 1;

            function findById(source, id) {
                for (var i = 0; i < source.length; i++) {
                    if (source[i].questionId === id) {
                        return source[i].answerId;
                    }
                }
            }
        }
    };
});