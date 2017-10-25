"use strict";
app.controller('homeController', function ($state, $window, $filter) {
    $window.document.title = "Shutaf-In | " +$filter('translate')('Home.title');
});