app.controller('questionsCtrl', function ($scope, $state, quizModel, notify, $sessionStorage, $filter, $window) {
    $window.document.title = "Shutaf-In | " +$filter('translate')('Questions.title');
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
            scope.disableSubmit = true;
            var questions = $sessionStorage.questions;
            scope.currentPage = 1;
            scope.currentQuestion = questions[scope.currentPage - 1];
            var answers = $sessionStorage.selectedAnswers;
            scope.currentAnswer = findById(answers, scope.currentQuestion.questionId);
            var previousAnswer = scope.currentAnswer;
            var previousQuestionId = scope.currentQuestion.questionId;
            var numberOfAnswers = new Array();

            var flag = true;
            checkIfNull();
            if (flag) {
                scope.disableSubmit = false;
            }

            scope.newValue = function () {
                checkIfNull();
                addToNumberOfAnswers(scope.currentQuestion.questionId);
                if (numberOfAnswers.length === questions.length || flag) {
                    scope.disableSubmit = false;
                }

            };
            scope.pageChanged = function () {
                var indexArray = scope.currentPage - 1;
                scope.currentQuestion = questions[indexArray];
                answers[findPosition(answers, previousQuestionId)].answerId = scope.currentAnswer;
                scope.currentAnswer = answers[findPosition(answers, scope.currentQuestion.questionId)].answerId;
                previousAnswer = scope.currentAnswer;
                previousQuestionId = scope.currentQuestion.questionId;
                $sessionStorage.selectedAnswers = answers;
            };

            scope.save = function () {
                answers[findPosition(answers, previousQuestionId)].answerId = scope.currentAnswer;
                $sessionStorage.selectedAnswers = answers;
                scope.backData();
                $state.go('userProfile', {id: $sessionStorage.userProfile.userId});
            };

            scope.totalItems = questions.length;
            scope.itemsPerPage = 1;

            function findById(source, id) {
                for (var i = 0; i < source.length; i++) {
                    if (source[i].questionId === id) {
                        return source[i].answerId;

                    }
                }
            }
            function findPosition(source, id) {
                for (var i = 0; i < source.length; i++) {
                    if (source[i].questionId === id) {
                        return i;
                    }
                }
            }
            function checkIfNull() {
                for (var i = 0; i < answers.length; i++) {
                    if (answers[i].answerId === null) {
                        flag = false;
                    }
                }
            }

            function addToNumberOfAnswers(value) {
                for (var i = 0; i < numberOfAnswers.length; i++) {
                    if (numberOfAnswers[i] === value) {
                        return;
                    }
                }
                numberOfAnswers.push(value);
            }
        }
    };
});