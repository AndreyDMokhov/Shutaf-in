"use strict";
app.controller('homeController', function ($state, $window) {
    $window.document.title = "Shutaf-In | " +$state.current.title;
});