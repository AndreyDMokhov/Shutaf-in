"use strict";
app.controller('aboutController', function ($state, browserTitle) {
    var vm = this;
    browserTitle.setBrowserTitleByFilterName('About.title');
});