app.directive('cropNameDirective', function () {
    return {
        restrict: "E",
        scope: {
            name: '@',
            characterLimit: '='
        },
        template: '<span>{{cropName()}}</span>',

        link: function (scope, element, attrs) {
            scope.cropName = function () {
                if (scope.name.length > scope.characterLimit) {
                    scope.name = scope.name.substring(0, parseInt(scope.characterLimit)) + '... ';
                }
                return scope.name;
            }
        }
    };
});