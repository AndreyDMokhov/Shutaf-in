"use strict";
app.controller('aboutController', function ($state, $window, $filter) {
    var vm = this;
    $window.document.title = "Shutaf-In | " +$filter('translate')('About.title');
});