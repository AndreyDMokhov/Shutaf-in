app.controller('userProfileController', function ($state, $filter, sessionService, userProfileModel, $sessionStorage, notify, $timeout ) {
    var vm = this;
    vm.userProfile = $sessionStorage.userProfile;
    console.log(vm.userProfile)


    vm.fileInfo = {};


    function setProfileImage() {
        if (!vm.userProfile.userImageId) {
            vm.image = '../../images/default_avatar.png';
            vm.deleteButton = true;
        }
        else {
            vm.image = 'data:image/jpeg;base64,' + vm.userProfile.userImage;
        }

    }

    function onLoad(e, reader, file, fileList, fileOjects, fileObj) {

        $timeout(function () {
            vm.image = 'data:image/jpeg;base64,' + vm.fileInfo.base64;
            vm.deleteButton = true;
            saveImage();
        }, 0);
    }

    function saveImage() {

        userProfileModel.addOrUpdateImage({image: vm.fileInfo.base64}).then(
            function (success) {
                console.log(success)
                vm.userProfile.userImage = success.data.image;
                vm.userProfile.userImageId = success.data.id;
                vm.userProfile.createdDate = success.data.createdDate;
                $sessionStorage.userProfile = vm.userProfile;
                vm.deleteButton = false;
                notify.set($filter('translate')('UserProfile.message.imageSaved'), {type: 'success'});
            },
            function (error) {
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
            }
        )
    }
    function deleteImage() {
        var confirmDeleting = confirm($filter('translate')('UserProfile.message.confirmDelete'));
        if (!confirmDeleting) {
            return;
        }
        userProfileModel.deleteImage().then(
            function (success) {
                vm.userProfile.userImage = '../../images/default_avatar.png';
                vm.userProfile.userImageId = null;
                $sessionStorage.userProfile = vm.userProfile;
                vm.image = '../../images/default_avatar.png';
                vm.deleteButton = true;
                notify.set($filter('translate')('UserProfile.message.imageDeleted'), {type: 'success'});
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