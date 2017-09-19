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
            // console.log(questions);
            // console.log(questions.length);
            scope.id = 0;


            // scope.currentQuestion = questions[scope.id];
            // // console.log(scope.currentQuestion);
            // scope.numberOfQuestions = questions.length;
            // var answers = $sessionStorage.selectedAnswers;
            //
            // scope.currentAnswer = $sessionStorage.selectedAnswers[scope.id].answerId;
            // console.log($sessionStorage.selectedAnswers);

            scope.currentPage = 1;

            scope.currentQuestion = questions[scope.currentPage - 1];
            console.log(scope.currentQuestion);
            scope.numberOfQuestions = questions.length;
            var answers = $sessionStorage.selectedAnswers;
            console.log(answers);
            scope.currentAnswer = $sessionStorage.selectedAnswers[scope.currentPage - 1].answerId;
            console.log(scope.currentAnswer);

            var currId = scope.currentPage;

            scope.pageChanged = function () {
                console.log(answers);
                console.log(scope.currentQuestion.questionId);
                answers[currId - 1].questionId = scope.currentQuestion.questionId;
                answers[currId - 1].answerId = scope.currentAnswer;
                currId = scope.currentPage;
                $sessionStorage.selectedAnswers = answers;
                scope.currentQuestion = questions[currId - 1];
                console.log(scope.currentQuestion.questionId);
                scope.currentAnswer = answers[currId - 1].answerId;
                console.log(answers);
            };

            scope.save = function () {
                answers[currId - 1].questionId = scope.currentQuestion.questionId;
                answers[currId - 1].answerId = scope.currentAnswer;
                $sessionStorage.selectedAnswers = answers;
                // $sessionStorage.selectedAnswers =
                //     [
                //         {
                //             questionId: 0, answerId: 1
                //
                //         },
                //         {
                //             questionId: 1, answerId: 4
                //
                //         },
                //         {
                //             questionId: 2, answerId: 6
                //
                //         },
                //         {
                //             questionId: 3, answerId: 11
                //
                //         }
                //     ];

                scope.backData();
                $state.go('userProfile', {id: $sessionStorage.userProfile.userId});
            };
            scope.dis = false;

            scope.totalItems = scope.numberOfQuestions;
            scope.itemsPerPage = 1;

            // scope.setPage = function (pageNo) {
            //     scope.currentPage = pageNo;
            // };
            //


            // scope.maxSize = 5;
            // scope.bigTotalItems = 175;
            // scope.bigCurrentPage = 1;
        }
    };
});