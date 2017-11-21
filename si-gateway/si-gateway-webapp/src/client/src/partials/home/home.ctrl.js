"use strict";
app.controller('homeController', function ($state, browserTitle) {
    browserTitle.setBrowserTitleByFilterName('Home.title');
});