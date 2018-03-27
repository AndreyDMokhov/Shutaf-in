"use strict";
app.component('userProfileInfo', {
    templateUrl: 'partials/userProfile/userProfileInfo/userProfileInfo.component.html',
    bindings: {
        userProfile: '='
    },
    controllerAs: 'vm',
    controller: function ($sessionStorage, quizModel, notify, $filter) {
        var vm = this;
        vm.cities = $sessionStorage.cities;
        vm.genders = $sessionStorage.genders;
        vm.countries = $sessionStorage.countries;
        vm.selectedAnswers = [];
        vm.questions = $sessionStorage.questions;
        vm.requiredQuestionsAndAnswers = [];

        function getRequiredAnswers() {
            if(vm.userProfile && vm.userProfile.userId){
                quizModel.getRequiredAnswers(vm.userProfile.userId).then(
                    function (success) {
                        if(success.data){
                            vm.selectedAnswers = success.data.data;
                            getDescriptionsForRequiredQuestions();
                        }
                    },
                    function (error) {
                        notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                    }
                );
            }
        }

        function getDescriptionsForRequiredQuestions() {
            if (!vm.selectedAnswers || vm.selectedAnswers.length < 1) {
                return;
            }
            angular.forEach(vm.selectedAnswers, function (selectedAnswer) {
                var questionId = selectedAnswer.questionId,
                    answerId = selectedAnswer.selectedAnswersIds[0];

                var question = vm.questions.find(function (question) {
                    return question.questionId === questionId;
                });
                if (!question) {
                    return;
                }
                var answer = question.answers.find(function (answer) {
                    return answer.answerId === answerId;
                });
                vm.requiredQuestionsAndAnswers.push({question: question.description, answer: answer.description});
            });
        }

        getRequiredAnswers();
    }
});