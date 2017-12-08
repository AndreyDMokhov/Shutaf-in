app.component('extendedQuestionComponent', {
    templateUrl: 'partials/questions/extendedQuestions/extendedQuestion.component.html',
    bindings: {
        sendData: '=',
        putAnswers: '=',
        listQuestions: '=',
        selectedAnswers: '='
    },
    controller: function ($sessionStorage, $state) {
        console.log(this.listQuestions);
        console.log(this.selectedAnswers);
        this.disableSubmit = true;
        var questions = this.listQuestions;
        // this.selectedBrands =[true, false, true];
        this.selectedBrands =[];

        this.currentPage = 1;
        this.currentQuestion = questions[this.currentPage - 1];
        console.log(this.currentQuestion.answers);
        // selectSearchFields = Object.keys(this.cars[0]);
        var answers = this.selectedAnswers;
        this.currentAnswer = findById(answers, this.currentQuestion.questionId);
        var previousAnswer = this.currentAnswer;
        var previousQuestionId = this.currentQuestion.questionId;
        var numberOfAnswers = new Array();

        // var flag = true;
        // checkIfNull();

        this.newValue = function () {
            // checkIfNull();
            addToNumberOfAnswers(this.currentQuestion.questionId);

        };
        this.pageChanged = function () {
            this.selectedBrands=[];
            var indexArray = this.currentPage - 1;
            this.currentQuestion = questions[indexArray];
            answers[findPosition(answers, previousQuestionId)].answerId = this.currentAnswer;
            this.currentAnswer = answers[findPosition(answers, this.currentQuestion.questionId)].answerId;
            previousAnswer = this.currentAnswer;
            previousQuestionId = this.currentQuestion.questionId;
            this.putAnswers(answers);
        };

        // this.save = function () {
        //     answers[findPosition(answers, previousQuestionId)].answerId = this.currentAnswer;
        //     this.sendData();
        //     this.putAnswers(answers);
        //     $state.go('userProfile', {id: $sessionStorage.userProfile.userId});
        // };
        this.save = function () {
            // for(var i in this.currentQuestion.answers){
            //     console.log(i);
            //     // alert(i); // alerts key
            //     // alert(this.currentQuestion.answers[i]); //alerts key's value
            // }
            // var arr1 = Object.keys(this.currentQuestion.answers);
            // console.log(arr1);

            console.log(Object.keys(this.currentQuestion.answers));
            console.log(Object.values(this.currentQuestion.answers));

            console.log(Object.keys(this.currentQuestion.answers).length);

            console.log(this.selectedBrands);
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

        // function checkIfNull() {
        //     for (var i = 0; i < answers.length; i++) {
        //         if (answers[i].answerId === null) {
        //             flag = false;
        //         }
        //     }
        // }

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