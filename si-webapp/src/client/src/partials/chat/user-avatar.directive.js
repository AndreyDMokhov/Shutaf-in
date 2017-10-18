angular.module('app').directive('userAvatar', function() {
    return {
        restrict: "E",
        template: '<img src="//robohash.org/{{uuid}}?set=set2&bgset=bg2&size={{size}}" alt="{{uuid}}" class="logo-center">',
        scope: {
            uuid: "@",
            size: "@"
        }
    };
});
