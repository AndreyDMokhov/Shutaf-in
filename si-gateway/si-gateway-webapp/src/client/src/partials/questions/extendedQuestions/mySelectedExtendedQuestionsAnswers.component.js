"use strict";
app.component('mySelectedExtendedQuestionsAnswersComponent', {
    templateUrl: 'partials/questions/extendedQuestions/mySelectedExtendedQuestionsAnswers.component.html',
    bindings: {},
    controller: function ($sessionStorage, $state) {
        this.listQuestionAnswersResponses = $sessionStorage.questionsExtended;
        this.listSelectedAnswersResponses = $sessionStorage.selectedExtendedAnswers;
    }

});