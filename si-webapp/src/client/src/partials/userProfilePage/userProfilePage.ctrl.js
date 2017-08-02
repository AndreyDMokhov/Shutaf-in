app.controller('userProfilePage', function ($state, $filter, sessionService, userProfileModel, $sessionStorage, notify, $timeout ) {
    var vm = this;
    vm.userProfile = $sessionStorage.userProfile;

    if (vm.userProfile.userImageId === null) {
        vm.avatarImage = '../../images/default_avatar.png'
        vm.deleteButton = true;
        vm.addButton = true;
    }
    else {
        vm.avatarImage = 'data:image/jpeg;base64,' + vm.userProfile.userImage
        vm.addButton = true;
    }
    vm.fileInfo = {};

    function onLoad(e, reader, file, fileList, fileOjects, fileObj) {
        $timeout(function () {
            vm.avatarImage = 'data:image/jpeg;base64,' + vm.fileInfo.base64;
            vm.addButton = false;
            vm.deleteButton = true;
        }, 0);

    };

    function addImage() {
        var imageB64 = {
            image: vm.fileInfo.base64
        }
        userProfileModel.addImage(imageB64).then(
            function (success) {
                var userProfile = vm.userProfile;
                userProfile.userImage = vm.fileInfo.base64;
                userProfile.userImageId = -1;
                sessionStorage.setItem("userProfile", JSON.stringify(userProfile));
                vm.deleteButton = false;
                vm.addButton = true;
                notify.set($filter('translate')('Profile.message.imageSaved'), {type: 'success'});
            },
            function (error) {
                notify.set($filter('translate')('Profile.message.errorSave'), {type: 'error'});
            }
        )
    };

    function deleteImage() {
        {
            var x = confirm($filter('translate')('Profile.message.confirmDelete'));
            if (x) {
                userProfileModel.deleteImage().then(
                    function (success) {
                        var userProfile = vm.userProfile;
                        userProfile.userImage = '../../images/default_avatar.png';
                        userProfile.userImageId = null
                        sessionStorage.setItem("userProfile", JSON.stringify(userProfile));
                        vm.avatarImage = '../../images/default_avatar.png';
                        vm.deleteButton = true;
                        vm.addButton = true;
                        notify.set($filter('translate')('Profile.message.imageDeleted'), {type: 'success'});
                    },
                    function (error) {
                        notify.set($filter('translate')('Profile.message.errorDelete'), {type: 'error'});

                    }
                )
                return true;
            }
            else
                return false;
        }
    }

    vm.onLoad = onLoad
    vm.addImage = addImage;
    vm.deleteImage = deleteImage
});
