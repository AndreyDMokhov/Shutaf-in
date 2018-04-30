"use strict";
app.component('questionComponent', {
    templateUrl: 'partials/questions/requiredQuestions/requiredQuestion.component.html',
    bindings: {
        sendData: '=',
        putAnswers: '=',
        listQuestions: '=',
        selectedAnswers: '='
    },
    controllerAs: 'vm',
    controller: function ($scope, $sessionStorage, $state) {
        var vm = this;

        vm.disableSubmit = true;
        var questions = vm.listQuestions;
        vm.currentPage = 1;
        vm.currentQuestion = questions[vm.currentPage - 1];
        var answers = vm.selectedAnswers;
        vm.currentAnswer = findById(answers, vm.currentQuestion.questionId);
        var previousAnswer = vm.currentAnswer;
        var previousQuestionId = vm.currentQuestion.questionId;
        var numberOfAnswers = new Array();

        var flag = true;
        checkIfNull();
        if (flag) {
            vm.disableSubmit = false;
        }

        vm.newValue = function () {

            if (vm.currentPage < questions.length) {
                vm.nextQuestion();
            }

            checkIfNull();
            addToNumberOfAnswers(vm.currentQuestion.questionId);
            if (numberOfAnswers.length === questions.length || flag) {
                vm.disableSubmit = false;
            }
            vm.pageChanged();
        };
        vm.previousPage = function () {
            if (vm.currentPage > 1) {
                vm.currentPage--;
                vm.pageChanged();
            }

        };
        vm.nextQuestion = function () {
            vm.currentPage++;
        };

        vm.nextPage = function () {
            if (vm.currentPage < questions.length) {
                vm.currentPage++;
                vm.pageChanged();
            }

        };
        vm.pageChanged = function () {
            var indexArray = vm.currentPage - 1;
            vm.currentQuestion = questions[indexArray];
            answers[findPosition(answers, previousQuestionId)].answerId = vm.currentAnswer;
            vm.currentAnswer = answers[findPosition(answers, vm.currentQuestion.questionId)].answerId;
            previousAnswer = vm.currentAnswer;
            previousQuestionId = vm.currentQuestion.questionId;
            vm.putAnswers(answers);
        };

        vm.save = function () {
            answers[findPosition(answers, previousQuestionId)].answerId = vm.currentAnswer;
            vm.sendData();
            vm.putAnswers(answers);
            if ($sessionStorage.showExtendedQuestions) {
                $state.go('myUserProfile');
            }
            else {
                $sessionStorage.showExtendedQuestions = true;
                $state.go('questionsTab.extendedQuestions', {}, {reload: true});
            }
        };

        vm.totalItems = questions.length;
        vm.itemsPerPage = 1;

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

        //This section intercepts rights and left arrow events and triggers next and previous question
        var angularDocument = angular.element(document);

        angularDocument.on('keydown', navigateToQuestion);
        $scope.$on('$destroy',function(){
            angularDocument.off('keydown', navigateToQuestion);
        });

        function navigateToQuestion(event) {

            if (event.keyCode === 39) {
                vm.nextPage();
            } else if (event.keyCode === 37) {
                vm.previousPage();
            }
            $scope.$apply();
        }



    }

});