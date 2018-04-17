"use strict";
app.component('layoutComponent', {
    templateUrl: 'layout/layout.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function ($state) {
        var vm = this;
        vm.state = $state;
    }
});