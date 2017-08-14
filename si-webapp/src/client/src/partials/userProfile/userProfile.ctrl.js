app.controller('userProfileController', function ($state, $filter, sessionService, userProfileModel, $sessionStorage, notify, $timeout ) {
    var vm = this;
    vm.userProfile = $sessionStorage.userProfile;

    function setCurrentAvatar() {
        if (vm.userProfile.userImageId === null) {
            vm.avatarImage = '../../images/default_avatar.png';
            vm.deleteButton = true;
            vm.addButton = true;
        }
        else {
            vm.avatarImage = 'data:image/jpeg;base64,' + vm.userProfile.userImage;
            vm.addButton = true;
        }
    }

    setCurrentAvatar();

    vm.fileInfo = {};

    function onLoad(e, reader, file, fileList, fileOjects, fileObj) {
        $timeout(function () {
            vm.avatarImage = 'data:image/jpeg;base64,' + vm.fileInfo.base64;
            vm.addButton = false;
            vm.deleteButton = true;
            saveImage();
        }, 0);

    }
    function saveImage() {
        var imageB64 = {
            image: vm.fileInfo.base64
        };
        userProfileModel.addImage(imageB64).then(
            function (success) {
                var userProfile = vm.userProfile;
                userProfile.userImage = vm.fileInfo.base64;
                userProfile.userImageId = -1;
                $sessionStorage.userProfile = userProfile;
                vm.deleteButton = false;
                vm.addButton = true;
                notify.set($filter('translate')('Profile.message.imageSaved'), {type: 'success'});
            },
            function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
            }
        )
    }
    function deleteImage() {
        var confirmDeleting = confirm($filter('translate')('Profile.message.confirmDelete'));
        if (!confirmDeleting) {
            return null;
        }
        userProfileModel.deleteImage().then(
            function (success) {
                var userProfile = vm.userProfile;
                userProfile.userImage = '../../images/default_avatar.png';
                userProfile.userImageId = null;
                $sessionStorage.userProfile = userProfile;
                vm.avatarImage = '../../images/default_avatar.png';
                vm.deleteButton = true;
                vm.addButton = true;
                notify.set($filter('translate')('Profile.message.imageDeleted'), {type: 'success'});
            },
            function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
            });
    }

    vm.onLoad = onLoad;
    vm.saveImage = saveImage;
    vm.deleteImage = deleteImage;
});
