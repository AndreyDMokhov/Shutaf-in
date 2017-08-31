app.controller('QuestionsCtrl', function ($scope, $state, quizFactory) {
    var result = [];
    $scope.answersFromController = [];
    $scope.questionsFromController = [];
    $scope.back = function (data) {
        console.log(data);
        $state.go('home')
    };
    $scope.coooo = function () {
        // console.log($scope.answersFromController);
    }

    $scope.isReady = false;

    function getQuestion() {
        // var deferred = $q.defer();
        // quizFactory.get123(); ->>    LETTER. We don't pay with letters. We pay with money. Open the letter
        //get123 -> письмо
        //then -> открывашка
        //money -> деньги
        quizFactory.get123().then(function (money) {

            $scope.questionsFromController = money;
            $scope.isReady = true;
            // return deferred.resolve($scope.questionsFromController);
        }, function (noMoney) {

        });
        // return deferred.promise;
    }

    getQuestion();

    // function test() {
    //     getQuestion().then(function (result) {
    //
    //         console.log('!')
    //         console.log($scope.questionsFromController)
    //     })
    // }
    // console.log('ttt')
    // test();

});


app.directive('quiz', function () {
    return {
        scope: {
            questionsToDirective: '=',
            answerToDirective: '=',
            backD:'&',
            returnAnswer: '='
        },
        restrict: 'AE',
        templateUrl: 'partials/questions/questionTemplate.html',
        link: function (scope, elem, attrs) {
            var questions = scope.questionsToDirective;
            var answer = scope.answerToDirective;
            var back = scope.back;
            var rrr = scope.returnAnswer;
            // console.log(questions)
            scope.inProgress = true;

            scope.id = 0;
            scope.currentQuestion = getQuestion1(scope.id);
            // console.log(scope.currentQuestion.answers);

            scope.nextQuestion = function () {
                var result = {"idQ": scope.currentQuestion.id, "idA": scope.currentAnswer};
                answer.push(result.idA);
                scope.id++;
                scope.currentQuestion =getQuestion1(scope.id);
                if (!scope.currentQuestion) {
                    scope.inProgress = false;
                    console.log(answer);
                    scope.backD({data: answer});
                }
                scope.currentAnswer = null;
            };

            function getQuestion1(id) {
                if (id < questions.length) {
                    return questions[id];
                } else {
                    return false;
                }
            }
        }
    }
});

// (function(angular) {
//     'use strict';
//
//         app.controller('QuestionsCtrl', ['$scope', function($scope,quizFactory) {
//             $scope.customer = {
//                 name: 'Naomi',
//                 address: '1600 Amphitheatre'
//             };
//             //
//             $scope.answers = [];
//     $scope.questions=123;
//     $scope.back = function () {
//         $state.go('home')
//     };
//                 function getQuestion() {
//         quizFactory.get123().then(
//             function (success) {
//                 $scope.questions = success.data.data;
//                 console.log($scope.questions)
//             },
//             function (error) {
//                 console.log("---")
//                 console.log(error)
//
//             }
//         )
//
//     }
//         getQuestion();
//             console.log($scope.questions)
//
//     // function getQuestion() {
//     //     quizFactory.get123().then(
//     //         function (success) {
//     //             $scope.questions = success.data.data;
//     //             console.log($scope.questions)
//     //         },
//     //         function (error) {
//     //             console.log("---");
//     //             console.log(error)
//     //
//     //         }
//     //     )
//     //
//     // }
//     //     getQuestion();
//
//
//         }]);
//         app.directive('myCustomer', function() {
//             // return {
//             //     template: 'Name: {{questions}} Address: {{customer.address}}'
//             // };
//
//             // app.directive('quiz', function (quizFactory) {
//
//             return {
//
//                 restrict: 'AE',
//                 templateUrl: 'partials/questions/questionTemplate.html',
//                 controller: function ($scope) {
//
//                 },
//                 link: function (scope, elem, attrs, $scope) {
//                     var questions = [
//                         {
//                             id:5,
//                             question: "Question1",
//                             options: ["answer1", "answer2", "answer3", "answer4"],
//                         },
//                         { id:6,
//                             question: "Question2",
//                             options: ["answer1", "answer2"],
//                         },
//                         {
//                             id:7,
//                             question: "Question3",
//                             options: ["answer1", "answer2", "answer3"],
//                         },
//                         {
//                             id:8,
//                             question: "Question4",
//                             options: ["answer1", "answer2", "answer3", "answer4"],
//                         },
//                         {
//                             id:9,
//                             question: "Question5",
//                             options: ["answer1", "answer2", "answer3", "answer4"],
//
//                         }
//                     ];
//                     scope.inProgress = true;
//                     scope.currentAnswer;
//                     scope.id = 0
//                     scope.currentQuestion = getQuestion1(scope.id);
//
//
//                     scope.nextQuestion = function () {
//                         var result = {"idQ": scope.currentQuestion.id, "idA": scope.currentAnswer}
//                         scope.answers.push(result)
//                         scope.id++;
//                         scope.currentQuestion = getQuestion1(scope.id);
//                         if (!scope.currentQuestion) {
//                             scope.inProgress = false;
//                             console.log(scope.answers);
//                             scope.back();
//                         }
//                         scope.currentAnswer = null;
//                     };
//                     function getQuestion1(id) {
//                         if (id < questions.length) {
//                             return questions[id];
//                         } else {
//                             return false;
//                         }
//                     }
//                 }
//             }
//
//
//
//
//         });
// })(window.angular);

//
//     $scope.questionsFromController = 'govno';
//     console.log(  $scope.questionsFromController)
//     function getQuestion() {
//         quizFactory.get123().then(
//             function (success) {
//                 $scope.questionsFromController = success.data.data;
//                 console.log(  $scope.questionsFromController)
//             },
//             function (error) {
//                 console.log(error)
//             }
//         )}
//     getQuestion();
// console.log(  $scope.questionsFromController)