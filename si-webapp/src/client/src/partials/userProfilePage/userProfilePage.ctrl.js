app.controller('userProfilePage', function ($state, $filter, sessionService, $scope, userProfileModel, CACHED_USER_IMAGE_ID) {
    var vm = this;
    vm.userProfile = JSON.parse(sessionStorage.getItem('userProfile'));
    if (userProfileModel.getDataImage() === null) {
        vm.avatarImage = '../../images/default_avatar.png'
    }
    else {
        userProfileModel.getDataImage().then(function (result) {
            var imageBase64 = result.data.image;
            vm.avatarImage = 'data:image/jpeg;base64,' + imageBase64
        });

    }


    var imageId = JSON.parse(localStorage.getItem(CACHED_USER_IMAGE_ID));
    vm.fileInfo = {};

    function onLoad(e, reader, file, fileList, fileOjects, fileObj) {
    };

    function addImage() {
        console.log(vm.fileInfo)

        if (vm.fileInfo.base64 == undefined) {
            alert("choose image")
            return null;
        }
        var imageB64 = {
            image: vm.fileInfo.base64
        }
        userProfileModel.addImage(imageB64).then(
            function (success) {
                var imId = {imageId: success.data.id}
                vm.avatarImage = 'data:image/jpeg;base64,' + vm.fileInfo.base64;
                localStorage.setItem(CACHED_USER_IMAGE_ID, JSON.stringify(imId));
                // vm.userProfile.imageID = success.data.id

            },
            function (error) {
                alert("error")

            }
        )
    };

    function getList() {
        userProfileModel.getImages().then(
            function (success) {
                console.log(success)
            },
            function(error){
                console.log(error)
            }
        )
    };

    vm.onLoad = onLoad
    vm.addImage = addImage;
    vm.getList = getList
});
