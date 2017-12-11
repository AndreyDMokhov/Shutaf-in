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
        vm.AnswersWithIDForCurrentPage = [];
        vm.currentPage = 1;
        vm.previousPage = 1;
        var fullQuestionObjectForCurrentPage = questions[vm.currentPage - 1];
        var fullAnswerObjectForCurrentPage = answers[vm.currentPage - 1];
        var valuesOfCheckboxesCurrentPage = [];
        vm.selectedCheckboxes = getArrayofAlreadyAnswersForCurrentPage(fullQuestionObjectForCurrentPage.answers, vm.currentPage);
        vm.AnswersWithIDForCurrentPage = getArrayOfAnswersWithIDForCurrentPage(fullQuestionObjectForCurrentPage.answers);
        vm.variantsOfAnswersImportance = answers[vm.currentPage - 1].questionImportanceId === null ? 1 : answers[vm.currentPage - 1].questionImportanceId;

        vm.pageChanged = function () {
            addImportanceIdToAnswers();
            valuesOfCheckboxesCurrentPage = [];
            var indexArray = vm.currentPage - 1;
            fullQuestionObjectForCurrentPage = questions[indexArray];
            fullAnswerObjectForCurrentPage = answers[indexArray];
            vm.selectedCheckboxes = getArrayofAlreadyAnswersForCurrentPage(fullQuestionObjectForCurrentPage.answers, vm.currentPage);
            vm.variantsOfAnswersImportance = answers[indexArray].questionImportanceId === null ? 1 : answers[indexArray].questionImportanceId;
            vm.AnswersWithIDForCurrentPage = getArrayOfAnswersWithIDForCurrentPage(fullQuestionObjectForCurrentPage.answers);
            vm.previousPage = vm.currentPage;
        };

        vm.getValuesOfCheckBox = function (trueFalse, numberAnsw) {
            if (trueFalse) {
                valuesOfCheckboxesCurrentPage.push(parseInt(numberAnsw));
            }
            else {
                var index = valuesOfCheckboxesCurrentPage.indexOf(parseInt(numberAnsw));
                if (index > -1) {
                    valuesOfCheckboxesCurrentPage.splice(index, 1);
                }
            }
        };

        function getArrayOfAnswersWithIDForCurrentPage(answers) {
            var arr = [];
            for (var i in answers) {
                var element = {id: i, answer: answers[i]};
                arr.push(element);
            }
            return arr;
        }

        vm.save = function () {
            addImportanceIdToAnswers();
            this.putAnswers(answers);
            this.sendData();
            $state.go('userProfile', {id: $sessionStorage.userProfile.userId});


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
            var ooobb = [];
            var obj1 = Object.keys(fullQuestionObjectForCurrentPage);
            for (var i = 0; i < obj1.length; i++) {
                if (answers[currentPage - 1].answersId === null || !(answers[currentPage - 1].answersId).includes(parseInt(obj1[i]))) {
                    ooobb[i] = null;
                }
                else {
                    ooobb[i] = obj1[i];
                    valuesOfCheckboxesCurrentPage.push(parseInt(obj1[i]));
                }
                ;
            }
            return ooobb;
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
            else answers[vm.previousPage - 1].questionImportanceId = null;
        }
    }

});