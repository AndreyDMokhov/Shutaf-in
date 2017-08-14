app.controller('userProfileController', function ($state, $filter, sessionService, userProfileModel, $sessionStorage, notify, $timeout ) {
    var vm = this;
    vm.userProfile = $sessionStorage.userProfile;

    vm.fileInfo = {};


    function setProfileImage() {
        if (vm.userProfile.userImageId === null) {
            vm.image = '../../images/default_avatar.png';
            vm.deleteButton = true;
            vm.addButton = true;
        }
        else {
            vm.image = 'data:image/jpeg;base64,' + vm.userProfile.userImage;
            vm.addButton = true;
        }

    }

    function onLoad(e, reader, file, fileList, fileOjects, fileObj) {

        $timeout(function () {
            vm.image = 'data:image/jpeg;base64,' + vm.fileInfo.base64;
            vm.addButton = false;
            vm.deleteButton = true;
            saveImage();
        }, 0);
    }

    function saveImage() {

        userProfileModel.addImage({image: vm.fileInfo.base64}).then(
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
            return;
        }
        userProfileModel.deleteImage().then(
            function (success) {
                var userProfile = vm.userProfile;
                userProfile.userImage = '../../images/default_avatar.png';
                userProfile.userImageId = null;
                $sessionStorage.userProfile = userProfile;
                vm.image = '../../images/default_avatar.png';
                vm.deleteButton = true;
                vm.addButton = true;
                notify.set($filter('translate')('Profile.message.imageDeleted'), {type: 'success'});
            },
            function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
            });
    }


    setProfileImage();



    vm.onLoad = onLoad;
    vm.saveImage = saveImage;
    vm.deleteImage = deleteImage;
});
