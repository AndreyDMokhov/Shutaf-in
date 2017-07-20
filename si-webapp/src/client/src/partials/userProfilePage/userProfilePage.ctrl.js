app.controller('userProfilePage', function ($state, $filter, sessionService, $scope) {
    var vm = this;
    vm.userProfile = JSON.parse(sessionStorage.getItem('userProfile'));

    $scope.onChange = function (e, fileList) {
        alert('this is on-change handler!');
    };

    $scope.onLoad = function (e, reader, file, fileList, fileOjects, fileObj) {
        alert('this is handler for file reader onload event!');
    };

    var uploadedCount = 0;

    $scope.files = [];
});