app.controller('QuestionsCtrl', function ($scope, $state) {
    $scope.answers = [];
    $scope.back = function () {
        $state.go('home')
    }
});

app.directive('quiz', function (quizFactory) {
    return {
        restrict: 'AE',
        templateUrl: 'partials/questions/questionTemplate.html',

        link: function (scope, elem, attrs) {
            scope.inProgress = true;
            scope.currentAnswer;
            scope.id = 0
            scope.currentQuestion = quizFactory.getQuestion(scope.id);


            scope.nextQuestion = function () {
                var result = {"idQ": scope.currentQuestion.id, "idA": scope.currentAnswer}
                scope.answers.push(result)
                scope.id++;
                scope.currentQuestion = quizFactory.getQuestion(scope.id);
                if (!scope.currentQuestion) {
                    scope.inProgress = false;
                    console.log(scope.answers);
                    scope.back();
                }
                scope.currentAnswer = null;
            }
        }
    }
});