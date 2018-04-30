"use strict";
app.controller('aboutController', function ($state, browserTitleService) {
    var vm = this;
    browserTitleService.setBrowserTitleByFilterName('About.title');
});