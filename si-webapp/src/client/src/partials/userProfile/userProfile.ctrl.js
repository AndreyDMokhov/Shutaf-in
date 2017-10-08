"use strict";
app.controller('userProfileController', function ($localStorage,
                                                  $state,
                                                  $filter,
                                                  sessionService,
                                                  userProfileModel,
                                                  $sessionStorage,
                                                  notify,
                                                  $timeout,
                                                  $scope,
                                                  ngDialog,
                                                  IMAGE_MAX_SIZE_MB) {

    var vm = this;
    $scope.myCroppedImage='';
    vm.userProfile = $sessionStorage.userProfile;
    vm.fileInfo = {};
    vm.size = IMAGE_MAX_SIZE_MB * 1000;

    vm.cities = $sessionStorage.cities;
    vm.genders = $sessionStorage.genders;

    function setProfileImage() {
        if (!vm.userProfile.userImageId) {
            vm.image = '../../images/default_avatar.png';
            vm.deleteButton = true;
        }
        else {
            vm.image = 'data:image/jpeg;base64,' + vm.userProfile.userImage;
        }
    }

    $scope.onLoad = function (e, reader, file, fileList, fileOjects, fileObj) {
        $timeout(function () {
            $scope.myImage = 'data:image/jpeg;base64,' + vm.fileInfo.base64;
            vm.deleteButton = true;

            if (vm.size > vm.fileInfo.filesize / 1024) {
                $scope.showImagePopup();
            }
            else {
                setProfileImage();
                notify.set($filter('translate')('UserProfile.message.sizeImage', {size: vm.size/1000}), {type: 'warn'});
            }
        }, 0);


    };

    function saveImage(data) {
        userProfileModel.addOrUpdateImage({image: data}).then(
            function (success) {
                vm.userProfile.userImage = success.data.data.image;
                vm.userProfile.userImageId = success.data.data.id;
                vm.userProfile.createdDate = success.data.data.createdDate;
                $sessionStorage.userProfile = vm.userProfile;
                vm.deleteButton = false;
                notify.set($filter('translate')('UserProfile.message.imageSaved'), {type: 'success'});
            },

            function (error) {
                if (error === undefined || error === null) {
                    notify.set($filter('translate')('Error.SYS'), {type: 'error'});
                }

                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
            }
        );
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
    $scope.cropIt =function(data) {
        vm.image=data;
        var arr = data.split([',']);
        saveImage(arr[1]);
        ngDialog.close();
    };

    $scope.showImagePopup = function() {
        ngDialog.open({
            templateUrl: 'partials/userProfile/imagePopup.html',
            scope: $scope,
            showClose: false,
            className: 'ngdialog-theme-plain custom-width',
            closeByDocument: true
        });
    };

    setProfileImage();
    vm.saveImage = saveImage;
    vm.deleteImage = deleteImage;
});