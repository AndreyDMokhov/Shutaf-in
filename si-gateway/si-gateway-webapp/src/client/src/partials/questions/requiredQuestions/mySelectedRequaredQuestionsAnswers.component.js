"use strict";
app.component('mySelectedRequaredQuestionsAnswersComponent', {
    templateUrl: 'partials/questions/requiredQuestions/mySelectedRequaredQuestionsAnswers.component.html',
    bindings: {},
    controller: function ($sessionStorage, $state) {
        this.listQuestionAnswersResponses = $sessionStorage.questions;
        this.listSelectedAnswersResponses = $sessionStorage.selectedAnswers;
    }

});