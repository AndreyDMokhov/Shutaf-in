"use strict";
app.controller('aboutController', function ($state, $window) {
    var vm = this;
    $window.document.title = "Shutaf-In | " +$state.current.title;
});