app.controller('userProfilePage', function ($state, $filter, sessionService, $scope, userProfileModel, CACHED_USER_IMAGE_ID) {
    var vm = this;
    vm.userProfile = JSON.parse(sessionStorage.getItem('userProfile'));
    if (userProfileModel.XXX() === null) {
        vm.avatarImage = '../../images/default_avatar.png'
    }
    else {
        userProfileModel.XXX().then(function (result) {
            var imageBase64 = result.data.image;
            vm.avatarImage = 'data:image/jpeg;base64,' + imageBase64
        });

    }


    var imageId = JSON.parse(sessionStorage.getItem(CACHED_USER_IMAGE_ID));
    vm.fileInfo = {};

    function onLoad(e, reader, file, fileList, fileOjects, fileObj) {
    };

    function addImage() {
        console.log(vm.fileInfo)

        if (vm.fileInfo.base64 == undefined) {
            alert("choose image")
            return null;
        }
        var image = {
            image: vm.fileInfo.base64
        }
        userProfileModel.addImage(image).then(
            function (success) {
                var imId = {imageId: success.data.id}
                vm.avatarImage = 'data:image/jpeg;base64,' + vm.fileInfo.base64;
                sessionStorage.setItem(CACHED_USER_IMAGE_ID, JSON.stringify(imId));
                // vm.userProfile.imageID = success.data.id

            },
            function (error) {
                alert("error")

            }
        )
    };


    vm.onLoad = onLoad
    vm.addImage = addImage;
});
