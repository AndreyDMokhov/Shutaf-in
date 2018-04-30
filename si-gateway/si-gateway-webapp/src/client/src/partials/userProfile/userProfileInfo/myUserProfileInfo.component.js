"use strict";
app.component('myUserProfileInfo', {
    templateUrl: 'partials/userProfile/userProfileInfo/myUserProfileInfo.component.html',
    controllerAs: 'vm',
    controller: function ($sessionStorage,
                          accountStatus,
                          $filter) {
        var vm = this;
        vm.cities = $sessionStorage.cities;
        vm.genders = $sessionStorage.genders;
        vm.countries = $sessionStorage.countries;
        vm.userProfile = $sessionStorage.userProfile;
        vm.selectedAnswers = $sessionStorage.selectedAnswers;
        vm.selectedExtendedAnswers = $sessionStorage.selectedExtendedAnswers;
        vm.questions = $sessionStorage.questions;
        vm.questionsExtended = $sessionStorage.questionsExtended;
        vm.requiredQuestionsAndAnswers = [];
        vm.extendedQuestionsAndAnswers = [];

        vm.currentAccountStatus = ($sessionStorage.accountStatus != null) ? $sessionStorage.accountStatus : 0;

        vm.accountStatuses = accountStatus.Statuses;

        function getDescriptionsForRequiredQuestions() {
            if (vm.currentAccountStatus < vm.accountStatuses.COMPLETED_REQUIRED_MATCHING) {
                return;
            }
            if (!vm.selectedAnswers || vm.selectedAnswers.length < 1) {
                return;
            }
            angular.forEach(vm.selectedAnswers, function (selectedAnswer) {
                var questionId = selectedAnswer.questionId,
                    answerId = selectedAnswer.answerId;

                var question = vm.questions.find(function (question) {
                    return question.questionId === questionId;
                });
                if (!question) {
                    return;
                }
                var answer = {};
                if (selectedAnswer.answerId !== undefined && selectedAnswer.answerId !== null) {

                    answer = question.answers.find(function (answer) {
                        return answer.answerId === answerId;
                    });
                } else {
                    answer = {'answerId': null, 'description': $filter('translate')('UserProfile.answers.required.not-answered')};
                }
                vm.requiredQuestionsAndAnswers.push({question: question.description, answer: answer.description});
            });
        }

        function getDescriptionsForExtendedQuestions() {
            if (vm.currentAccountStatus < vm.accountStatuses.COMPLETED_REQUIRED_MATCHING) {
                return;
            }
            if (!vm.selectedExtendedAnswers || vm.selectedExtendedAnswers.length < 1) {
                return;
            }
            angular.forEach(vm.selectedExtendedAnswers, function (selectedAnswer) {
                var questionId = selectedAnswer.questionId,
                    answersId = selectedAnswer.answersId;

                var question = vm.questionsExtended.find(function (question) {
                    return question.questionId === questionId;
                });
                if (!question) {
                    return;
                }
                var answers = [];
                angular.forEach(answersId, function (id) {
                    if (question.answers[id]) {
                        answers.push(question.answers[id]);
                    }
                });
                vm.extendedQuestionsAndAnswers.push({question: question.questionDescription, answers: answers});
            });
        }

        getDescriptionsForRequiredQuestions();
        getDescriptionsForExtendedQuestions();
    }
});