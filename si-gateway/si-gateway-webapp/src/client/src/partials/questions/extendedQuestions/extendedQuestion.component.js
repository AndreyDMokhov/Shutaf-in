"use strict";
app.component('extendedQuestionComponent', {
    templateUrl: 'partials/questions/extendedQuestions/extendedQuestion.component.html',
    bindings: {
        sendData: '=',
        putAnswers: '=',
        listQuestions: '=',
        selectedAnswers: '='
    },
    controller: function ($sessionStorage, $state) {
        var vm = this;
        vm.questionImportance = $sessionStorage.questionImportance;

        var questions = vm.listQuestions;
        var answers = getanswersArrayInOrder(questions, vm.selectedAnswers);
        vm.answersWithIDForCurrentPage = [];
        vm.currentPage = 1;
        vm.previousPage = 1;
        var fullQuestionObjectForCurrentPage = questions[vm.currentPage - 1];
        vm.currentQuestion = fullQuestionObjectForCurrentPage.questionDescription;
        var fullAnswerObjectForCurrentPage = answers[vm.currentPage - 1];
        var valuesOfCheckboxesCurrentPage = [];
        vm.selectedCheckboxes = getArrayofAlreadyAnswersForCurrentPage(fullQuestionObjectForCurrentPage.answers, vm.currentPage);
        vm.answersWithIDForCurrentPage = getArrayOfanswersWithIDForCurrentPage(fullQuestionObjectForCurrentPage.answers);
        vm.variantsOfAnswersImportance = answers[vm.currentPage - 1].questionImportanceId === null ? 1 : answers[vm.currentPage - 1].questionImportanceId;

        function pageChanged() {
            addImportanceIdToAnswers();
            valuesOfCheckboxesCurrentPage = [];
            var indexArray = vm.currentPage - 1;
            fullQuestionObjectForCurrentPage = questions[indexArray];
            vm.currentQuestion = fullQuestionObjectForCurrentPage.questionDescription;
            fullAnswerObjectForCurrentPage = answers[indexArray];
            vm.selectedCheckboxes = getArrayofAlreadyAnswersForCurrentPage(fullQuestionObjectForCurrentPage.answers, vm.currentPage);
            vm.variantsOfAnswersImportance = answers[indexArray].questionImportanceId === null ? 1 : answers[indexArray].questionImportanceId;
            vm.answersWithIDForCurrentPage = getArrayOfanswersWithIDForCurrentPage(fullQuestionObjectForCurrentPage.answers);
            vm.previousPage = vm.currentPage;
        }

        function getValuesOfCheckBox(ifCheckBoxSelected, numberAnsw) {
            if (ifCheckBoxSelected) {
                valuesOfCheckboxesCurrentPage.push(parseInt(numberAnsw));
            }
            else {
                var index = valuesOfCheckboxesCurrentPage.indexOf(parseInt(numberAnsw));
                if (index > -1) {
                    valuesOfCheckboxesCurrentPage.splice(index, 1);
                }
            }
        };

        function getArrayOfanswersWithIDForCurrentPage(answers) {
            var arr = [];
            for (var i in answers) {
                var element = {id: i, answer: answers[i]};
                arr.push(element);
            }
            return arr;
        }

        function save() {
            addImportanceIdToAnswers();
            this.putAnswers(answers);
            this.sendData();
            $state.go('myUserProfile');


        };

        vm.totalItems = questions.length;
        vm.itemsPerPage = 1;

        function findById(source, id) {
            for (var i = 0; i < source.length; i++) {
                if (source[i].questionId === id) {
                    return source[i].answersId;
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

        function getArrayofAlreadyAnswersForCurrentPage(fullQuestionObjectForCurrentPage, currentPage) {
            var arrayOfExistingAnswers = [];
            var arrayOfIdOfAnswersForCurrentQuestion = Object.keys(fullQuestionObjectForCurrentPage);
            for (var i = 0; i < arrayOfIdOfAnswersForCurrentQuestion.length; i++) {
                if (answers[currentPage - 1].answersId === null || !(answers[currentPage - 1].answersId).includes(parseInt(arrayOfIdOfAnswersForCurrentQuestion[i]))) {
                    arrayOfExistingAnswers[i] = null;
                }
                else {
                    arrayOfExistingAnswers[i] = arrayOfIdOfAnswersForCurrentQuestion[i];
                    valuesOfCheckboxesCurrentPage.push(parseInt(arrayOfIdOfAnswersForCurrentQuestion[i]));
                }
                ;
            }
            return arrayOfExistingAnswers;
        }

        function getanswersArrayInOrder(questions, answers) {
            var arr = [];
            for (var i = 0; i < questions.length; i++) {
                for (var j = 0; j < questions.length; j++) {
                    if (answers[j].questionId === (questions[i].questionId)) {
                        arr.push(answers[j]);
                    }
                }
            }
            return arr;
        }

        function addImportanceIdToAnswers() {
            answers[vm.previousPage - 1].answersId = valuesOfCheckboxesCurrentPage;
            if (valuesOfCheckboxesCurrentPage.length) {
                answers[vm.previousPage - 1].questionImportanceId = vm.variantsOfAnswersImportance;
            }
            else {
                answers[vm.previousPage - 1].questionImportanceId = null;
            }
        };

        function previousPageButton () {
            if ( this.currentPage > 1 ){
                this.currentPage--;
                this.pageChanged();
            }

        };
        function nextPageButton () {
            if ( this.currentPage < questions.length ){
                this.currentPage++;
                this.pageChanged();
            }

        };

        vm.pageChanged = pageChanged;
        vm.getValuesOfCheckBox = getValuesOfCheckBox;
        vm.save = save;
        vm.previousPageButton = previousPageButton;
        vm.nextPageButton = nextPageButton;
    }

});