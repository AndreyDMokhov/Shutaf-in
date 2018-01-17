'use strict';
app.component('userSearchComponent', {
    templateUrl: 'partials/userSearch/userSearch.component.html',
    bindings: {},
    controllerAs: 'vm',
    controller: function (browserTitle) {

        browserTitle.setBrowserTitleByFilterName('Search.title');
    }

});