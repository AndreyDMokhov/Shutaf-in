app.controller('userProfilePage', function ($state, $filter, sessionService, $scope) {
    var vm = this;
    vm.userProfile = JSON.parse(sessionStorage.getItem('userProfile'));
    vm.infofiles={"filename":"AAA"};
    vm.avatarImage = '../../images/default_avatar.png';
    // console.log(vm.infofiles)
    $scope.onChange = function (e, fileList) {
        alert('this is on-change handler!');
    };

    function onLoad (e, reader, file, fileList, fileOjects, fileObj) {
        console.log(vm.infofiles)
    };

    function try1 () {
        onLoad();
        console.log(vm.infofiles.filename)

    };



    vm.onLoad = onLoad
    vm.try1= try1;
});
