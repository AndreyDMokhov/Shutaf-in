"use strict";
app.component('questionComponent', {
    templateUrl: 'partials/questions/requiredQuestions/requiredQuestion.component.html',
    bindings: {
        sendData: '=',
        putAnswers: '=',
        listQuestions: '=',
        selectedAnswers: '='
    },
    controller: function ($sessionStorage, $state) {
        this.disableSubmit = true;
        var questions = this.listQuestions;
        this.currentPage = 1;
        this.currentQuestion = questions[this.currentPage - 1];
        var answers = this.selectedAnswers;
        this.currentAnswer = findById(answers, this.currentQuestion.questionId);
        var previousAnswer = this.currentAnswer;
        var previousQuestionId = this.currentQuestion.questionId;
        var numberOfAnswers = new Array();

        var flag = true;
        checkIfNull();
        if (flag) {
            this.disableSubmit = false;
        }

        this.newValue = function () {

            if ( this.currentPage < questions.length ){
                this.nextQuestion();
            }

            checkIfNull();
            addToNumberOfAnswers(this.currentQuestion.questionId);
            if (numberOfAnswers.length === questions.length || flag) {
                this.disableSubmit = false;
            }
            this.pageChanged();


        };
        this.previousPage = function () {
            if ( this.currentPage > 1 ){
                this.currentPage--;
                this.pageChanged();
            }

        };
        this.nextQuestion = function () {
            this.currentPage++;
        };

        this.nextPage = function () {
            if ( this.currentPage < questions.length ){
                this.currentPage++;
                this.pageChanged();
            }

        };
        this.pageChanged = function () {
            var indexArray = this.currentPage - 1;
            this.currentQuestion = questions[indexArray];
            answers[findPosition(answers, previousQuestionId)].answerId = this.currentAnswer;
            this.currentAnswer = answers[findPosition(answers, this.currentQuestion.questionId)].answerId;
            previousAnswer = this.currentAnswer;
            previousQuestionId = this.currentQuestion.questionId;
            this.putAnswers(answers);
        };

        this.save = function () {
            answers[findPosition(answers, previousQuestionId)].answerId = this.currentAnswer;
            this.sendData();
            this.putAnswers(answers);
            if ($sessionStorage.showExtendedQuestions) {
                $state.go('myUserProfile');
            }
            else {
                $sessionStorage.showExtendedQuestions = true;
                $state.go('questionsTab.extendedQuestions', {}, {reload: true});
            }
        };

        this.totalItems = questions.length;
        this.itemsPerPage = 1;

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

});