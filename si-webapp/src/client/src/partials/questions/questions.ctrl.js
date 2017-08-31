app.controller('QuestionsCtrl', function ($scope, $state, quizFactory) {
    $scope.questionsFromController = [];
    $scope.back = function (data) {
        sendData(data);
        $state.go('home')
    };
    $scope.isReady = false;


    function sendData(send) {
        quizFactory.sendAnswers(send).then(
            function (success) {
                //todo
            },
            function (error) {
                //todo
            }
        );
    }

    function getQuestion() {
        quizFactory.getQuestions().then(
            function (success) {
                $scope.questionsFromController = success;
                $scope.isReady = true;
                //todo
            },
            function (error) {
                //todo
            });
    }

    getQuestion();
});


app.directive('quiz', function () {
    return {
        scope: {
            questionsToDirective: '=',
            backData: '&',
            returnAnswer: '='
        },
        restrict: 'AE',
        templateUrl: 'partials/questions/questionTemplate.html',
        link: function (scope, elem, attrs) {
            var questions = scope.questionsToDirective;
            var answer = [];
            scope.inProgress = true;
            scope.id = 0;
            scope.currentQuestion = getQuestionByID(scope.id);

            scope.nextQuestion = function () {
                var result = {"questionId": scope.currentQuestion.questionId, "answerId": scope.currentAnswer};
                answer.push(result);
                scope.id++;
                scope.currentQuestion = getQuestionByID(scope.id);
                if (!scope.currentQuestion) {
                    scope.inProgress = false;
                    scope.backData({data: answer});
                }
                scope.currentAnswer = null;
            };

            function getQuestionByID(id) {
                if (id < questions.length) {
                    return questions[id];
                } else {
                    return false;
                }
            }
        }
    }
});