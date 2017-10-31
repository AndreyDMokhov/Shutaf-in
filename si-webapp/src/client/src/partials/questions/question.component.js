app.component('ourComponent', {
    templateUrl: 'partials/questions/question.component.html',
    bindings: {
        sendData: '='
    },
    controller: function ($sessionStorage, $state) {
        this.disableSubmit = true;
        var questions = $sessionStorage.questions;
        this.currentPage = 1;
        this.currentQuestion = questions[this.currentPage - 1];
        var answers = $sessionStorage.selectedAnswers;
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
            checkIfNull();
            addToNumberOfAnswers(this.currentQuestion.questionId);
            if (numberOfAnswers.length === questions.length || flag) {
                this.disableSubmit = false;
            }

        };
        this.pageChanged = function () {
            var indexArray = this.currentPage - 1;
            this.currentQuestion = questions[indexArray];
            answers[findPosition(answers, previousQuestionId)].answerId = this.currentAnswer;
            this.currentAnswer = answers[findPosition(answers, this.currentQuestion.questionId)].answerId;
            previousAnswer = this.currentAnswer;
            previousQuestionId = this.currentQuestion.questionId;
            $sessionStorage.selectedAnswers = answers;
        };

        this.save = function () {
            answers[findPosition(answers, previousQuestionId)].answerId = this.currentAnswer;
            $sessionStorage.selectedAnswers = answers;
            this.sendData();
            $state.go('userProfile', {id: $sessionStorage.userProfile.userId});
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