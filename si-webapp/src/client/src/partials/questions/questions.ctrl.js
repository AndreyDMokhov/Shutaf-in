app.controller('QuestionsCtrl', function ($scope, $state, quizFactory, notify, $filter) {
    $scope.questionsFromController = [];
    $scope.back = function (data) {
        sendData(data);
        $state.go('home')
    };
    $scope.isReady = false;


    function sendData(send) {
        quizFactory.sendAnswers(send).then(
            function (success) {
                notify.set($filter('translate')('Questions.confirm'),{type: 'success'})
            },
            function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
            }
        );
    }

    function getQuestion() {
        quizFactory.getQuestions().then(
            function (success) {
                $scope.questionsFromController = success;
                $scope.isReady = true;
            },
            function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
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