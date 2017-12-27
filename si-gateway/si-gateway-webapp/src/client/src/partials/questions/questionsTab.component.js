"use strict";
app.component('questionsTab', {
    templateUrl: 'partials/questions/questionsTab.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function ($sessionStorage) {
        var vm = this;
        vm.showExtendedQuestions = $sessionStorage.showExtendedQuestions;
    }
});