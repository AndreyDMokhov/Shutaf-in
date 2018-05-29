"use strict";
app.component('userProfileImage', {
    templateUrl: 'partials/settings/userSettings/userProfileImage/userProfileImage.component.html',
    controllerAs: 'vm',
    controller: function ($state,
                          $filter,
                          myUserProfileModel,
                          $sessionStorage,
                          uiNotification,
                          $timeout,
                          $scope,
                          ngDialog,
                          FILE_MAX_SIZE_MB,
                          $window,
                          userSearchModel,
                          sessionStorageObserver) {

        var vm = this;
        $scope.myCroppedImage = '';
        vm.fileInfo = {};
        vm.size = FILE_MAX_SIZE_MB * 1024;
        vm.userProfile = $sessionStorage.userProfile;

        vm.hideDeleteImageButton = false;
        vm.isThisMyProfile = false;
        vm.isUploading = false;

        function setProfileImage() {
            if (!vm.userProfile.originalUserImage) {
                vm.image = '../../images/default_avatar.png';
                vm.deleteButton = true;
            }
            else {
                vm.image = 'data:image/jpeg;base64,' + vm.userProfile.originalUserImage;
            }
        }

        $scope.onLoad = function (e, reader, file, fileList, fileObjects, fileObj) {
            $timeout(function () {
                debugger;
                $scope.myImage = 'data:image/jpeg;base64,' + vm.fileInfo.base64;
                setImageSize();
                vm.deleteButton = true;
                if (vm.size > vm.fileInfo.filesize / 1024) {
                    showImagePopup();
                }
                else {
                    setProfileImage();
                    var message = $filter('translate')('UserProfile.message.sizeImage', {size: vm.size / 1024});
                    uiNotification.show(message, 'warn');
                }
            }, 0);


        };

        function saveImage(data) {
            var uploadingNotification = uiNotification.show("<h5>{{'UserProfile.image.uploading' | translate}}&nbsp;<span class='fa fa-spinner fa-spin'></span></h5>", 'warn', true);

            myUserProfileModel.addOrUpdateImage({image: data}).then(
                function (success) {
                    vm.userProfile.userImage = success.data.data.image;
                    vm.userProfile.userImageId = success.data.data.id;
                    vm.userProfile.createdDate = success.data.data.createdDate;
                    userSearchModel.getOriginalUserImageById(vm.userProfile.userId).then(
                        function (success) {
                            uploadingNotification.close();
                            vm.userProfile.originalUserImageId = success.data.data.id;
                            vm.userProfile.originalUserImage =  success.data.data.image;
                            $sessionStorage.userProfile = vm.userProfile;
                            vm.deleteButton = false;
                            uiNotification.show($filter('translate')('UserProfile.image.uploaded'), 'success');
                            sessionStorageObserver.notifyServiceObservers();

                        },
                        function (error) {
                            vm.userProfile.originalUserImage = data;
                            sessionStorageObserver.notifyServiceObservers();
                        }
                    );
                },
                function (error) {}
            );
        }

        function deleteImage() {
            var confirmDeleting = confirm($filter('translate')('UserProfile.message.confirmDelete'));
            if (!confirmDeleting) {
                return;
            }

            myUserProfileModel.deleteImage().then(
                function (success) {
                    vm.userProfile.userImage = '../../images/default_avatar.png';
                    vm.userProfile.userImageId = null;
                    vm.userProfile.originalUserImage = null;
                    $sessionStorage.userProfile = vm.userProfile;
                    vm.image = '../../images/default_avatar.png';
                    vm.deleteButton = true;
                    uiNotification.show($filter('translate')('UserProfile.message.imageDeleted'), 'success');
                    sessionStorageObserver.notifyServiceObservers();
                },
                function (error) {
                });
        }

        $scope.cropIt = function (base64Image) {
            vm.image = base64Image;
            var base64ImageWithoutPrefix = base64Image.split([',']);
            saveImage(base64ImageWithoutPrefix[1]);
            ngDialog.close();
        };


        function showImagePopup() {
            ngDialog.open({
                templateUrl: 'partials/userProfile/imagePopup.html',
                scope: $scope,
                showClose: false,
                className: 'ngdialog-theme-plain custom-width',
                closeByDocument: true
            });
        }

        function setImageSize() {
            var width, height, myBase64 = $scope.myImage;
            var img = new Image();
            img.src = myBase64;
            img.addEventListener('load', function () {
                width = img.width;
                height = img.height;
                if (width >= 1000 && height >= 1000) {
                    $scope.selectedSize = {value: {w: 1000, h: 1000}};
                }
                else {
                    if (width >= height) {
                        $scope.selectedSize = {value: {w: height, h: height}};
                    }
                    else {
                        $scope.selectedSize = {value: {w: width, h: width}};
                    }
                }
            });
        }

        setProfileImage();
        vm.deleteImage = deleteImage;
    }

});