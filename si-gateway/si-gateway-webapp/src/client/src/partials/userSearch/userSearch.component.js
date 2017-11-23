'use strict';
app.component('userSearchComponent', {
    templateUrl: 'partials/userSearch/userSearch.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function ($state,
                          $sessionStorage,
                          notify,
                          sessionService,
                          userSearchModel,
                          $stateParams,
                          $filter,
                          browserTitle) {

        var vm = this;
        browserTitle.setBrowserTitleByFilterName('Search.title');

    }

});