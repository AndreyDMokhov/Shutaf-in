angular.module('app').directive('userAvatar', function() {
    return {
        restrict: "E",
        template: '<img src="{{avatarUrl}}" alt="{{uuid}}" class="logo-center">',
        scope: {
            uuid: "@",
            size: "@"
        },
        controller: function($scope){
            // Generating a uniq avatar for the given uniq string provided using robohash.org service
            $scope.avatarUrl = '//robohash.org/' + $scope.uuid + '?set=set2&bgset=bg2&size='+$scope.size;
        }
    };
});
