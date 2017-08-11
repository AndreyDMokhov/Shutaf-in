app.controller('CarouselCtrl', function ($scope, Carousel) {
var vm=this;
vm.slides = [{"firstName" : 123}, {"firstName" : 234},{"firstName" : 345}, {"firstName" : 5345},{"firstName" : 6345} ];
// quiz.start();

});

app.directive('quiz', function(quizFactory) {
    return {
        restrict: 'AE',
        scope: {},
        templateUrl: 'partials/questionsCarousel/questionTemplate.html',
        link: function(scope, elem, attrs) {
            scope.start = function() {
                scope.id = 0;
                scope.quizOver = false;
                scope.inProgress = true;
                scope.getQuestion();
            };



            scope.reset = function() {
                scope.inProgress = false;
                scope.score = 0;
            }

            scope.getQuestion = function() {
                var q = quizFactory.getQuestion(scope.id);
                if(q) {
                    scope.question = q.question;
                    scope.options = q.options;
                    scope.answer = q.answer;
                    scope.answerMode = true;
                } else {
                    scope.quizOver = true;
                }
            };

            scope.checkAnswer = function() {
                // if(!$('input[name=answer]:checked').length) return;
                //
                // var ans = $('input[name=answer]:checked').val();
                //
                // if(ans == scope.options[scope.answer]) {
                //     scope.score++;
                //     scope.correctAns = true;
                // } else {
                //     scope.correctAns = false;
                // }

                scope.answerMode = false;
            };

            scope.nextQuestion = function() {
                scope.id++;
                scope.getQuestion();
            }

            // scope.reset();
            scope.start();
        }
    }
});