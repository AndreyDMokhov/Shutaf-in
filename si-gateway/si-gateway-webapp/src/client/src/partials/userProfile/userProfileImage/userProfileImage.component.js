"use strict";
app.component('userProfileImage', {
    templateUrl: 'partials/userProfile/userProfileImage/userProfileImage.component.html',
    bindings: {
        userProfile: '=',
        dialogUserId: '=',
        isMyProfile: '='
    },
    controllerAs: 'vm',
    controller: function ($localStorage,
                          $state,
                          $stateParams,
                          $filter,
                          sessionService,
                          userProfileModel,
                          $sessionStorage,
                          notify,
                          $timeout,
                          $scope,
                          ngDialog,
                          IMAGE_MAX_SIZE_MB,
                          $window) {

        var vm = this;
        $scope.myCroppedImage = '';
        vm.fileInfo = {};
        vm.size = IMAGE_MAX_SIZE_MB * 1024;

        vm.hideDeleteImageButton = false;
        vm.isThisMyProfile = false;
        vm.disableLoadImage = false;

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
                $scope.myImage = 'data:image/jpeg;base64,' + vm.fileInfo.base64;
                setImageSize();
                vm.deleteButton = true;

                if (vm.size > vm.fileInfo.filesize / 1024) {
                    showImagePopup();
                }
                else {
                    setProfileImage();
                    notify.set($filter('translate')('UserProfile.message.sizeImage', {size: vm.size / 1024}), {type: 'warn'});
                }
            }, 0);


        };

        function saveImage(data) {
            userProfileModel.addOrUpdateImage({image: data}).then(
                function (success) {
                    vm.userProfile.userImage = success.data.data.image;
                    vm.userProfile.userImageId = success.data.data.id;
                    vm.userProfile.createdDate = success.data.data.createdDate;
                    //todo change to original size image
                    vm.userProfile.originalUserImage = success.data.data.image;
                    $sessionStorage.userProfile = vm.userProfile;
                    vm.deleteButton = false;
                    notify.set($filter('translate')('UserProfile.message.imageSaved'), {type: 'success'});
                    $window.location.reload();
                },

                function (error) {
                    if (error === undefined || error === null) {
                        notify.set($filter('translate')('Error.SYS'), {type: 'error'});
                    }

                    if (error.data.error.errorTypeCode === 'INP') {
                        if (error.data.error.errors.indexOf('INP.image.LimitSize') > 0) {
                            notify.set($filter('translate')('UserProfile.message.sizeImage'), {type: 'warn'});
                        }
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
                    vm.userProfile.originalUserImage = null;
                    $sessionStorage.userProfile = vm.userProfile;
                    vm.image = '../../images/default_avatar.png';
                    vm.deleteButton = true;
                    notify.set($filter('translate')('UserProfile.message.imageDeleted'), {type: 'success'});
                    $window.location.reload();
                },
                function (error) {
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
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
                    $scope.selectedSize = {value: {w: 1000, h: 1000}} ;
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

        function loadSearchResultsUserProfile() {
            var profileId = null;
            var isModalRequest = vm.dialogUserId != null;

            if (!isModalRequest) {
                profileId = $stateParams.id;

                var hasProfileIdInParam = profileId !== undefined && profileId !== null && profileId !== '';

                if (!hasProfileIdInParam) {
                    $state.go('error', {code: '404'});
                }
            } else {

                profileId = vm.dialogUserId.id;
            }

            vm.isThisMyProfile = vm.userProfile.userId === profileId;

            vm.enableDisableProfileImageTooltip = true;


            userProfileModel.getSelectedUserProfile(profileId).then(
                function (success) {
                    if (success.data.data === null) {
                        $state.go('error', {code: 404});
                    }
                    vm.userProfile = success.data.data;
                    if (!vm.userProfile.originalUserImage) {
                        vm.image = '../../images/default_avatar.png';
                    }
                    else {
                        vm.image = 'data:image/jpeg;base64,' + vm.userProfile.originalUserImage;
                    }
                },
                function (error) {
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                }
            );

        }

        loadSearchResultsUserProfile();
        setProfileImage();
        vm.deleteImage = deleteImage;
    }


});