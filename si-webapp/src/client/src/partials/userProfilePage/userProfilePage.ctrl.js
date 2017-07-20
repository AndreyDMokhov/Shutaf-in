app.controller('userProfilePage', function ($state, $filter, sessionService, $scope) {
    var vm = this;
    vm.userProfile = JSON.parse(sessionStorage.getItem('userProfile'));
    vm.infofiles={"filename":"AAA"};
    vm.avatarImage = '../../images/default_avatar.png';
    $scope.onChange = function (e, fileList) {
        alert('this is on-change handler!');
    };

    function onLoad (e, reader, file, fileList, fileOjects, fileObj) {
        console.log(vm.infofiles)
        };

    function try1 () {
        var number=vm.infofiles.base64
        console.log(vm.infofiles.filename)
        console.log(number)
        // console.log('"data:image/jpeg;base64,' + vm.number + '"')


        vm.avatarImage='data:image/jpeg;base64,' + vm.infofiles.base64
    };



    vm.onLoad = onLoad
    vm.try1= try1;
});
