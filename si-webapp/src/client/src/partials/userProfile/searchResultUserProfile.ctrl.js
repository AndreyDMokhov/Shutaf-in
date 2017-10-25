/**
 * Created by evgeny on 10/24/2017.
 */
"use strict";
app.controller('searchResultUserProfileController', function ($filter,
                                                  searchResultUserProfileModel,
                                                  $sessionStorage,
                                                  notify,
                                                  $location) {

    var vm = this;
    vm.userProfile = $sessionStorage.userProfile;

    vm.cities = $sessionStorage.cities;
    vm.genders = $sessionStorage.genders;

    function setProfileImage() {
        if (!vm.userProfile.userImageId) {
            vm.image = '../../images/default_avatar.png';
        }
        else {
            vm.image = 'data:image/jpeg;base64,' + vm.userProfile.originalUserImage;
        }
    }

    function loadUserProfile(){
        var urlElemensArr = $location.url().split('/');
        var userId = urlElemensArr[urlElemensArr.length - 1];
        searchResultUserProfileModel.getSelectedUserProfile(userId).then(
            function (success){
                vm.userProfile = success.data.data;
            },
            function (error){
                notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
            }
        );
    }

    setProfileImage();
    loadUserProfile();
});
