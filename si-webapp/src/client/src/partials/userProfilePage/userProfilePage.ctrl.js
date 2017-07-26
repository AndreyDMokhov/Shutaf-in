app.controller('userProfilePage', function ($timeout, $q,$state, $filter, sessionService, $scope, userProfileModel, CACHED_USER_IMAGE_ID, Lightbox, $templateCache) {
    var vm = this;
    vm.userProfile = JSON.parse(sessionStorage.getItem('userProfile'));
    if (userProfileModel.getDataImage() === null) {
        vm.avatarImage = '../../images/default_avatar.png'
    }
    else {
        vm.avatarImage = 'data:image/jpeg;base64,' + vm.userProfile.userImage
    }
    vm.fileInfo = {};

    function onLoad(e, reader, file, fileList, fileOjects, fileObj) {
        $timeout(function(){
            vm.avatarImage = 'data:image/jpeg;base64,' + vm.fileInfo.base64;
        },0);

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
                // localStorage.setItem(CACHED_USER_IMAGE_ID, JSON.stringify(imId));
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
            function (error) {
                console.log(error)
            }
        )
    };

    vm.images1 = [
        {
            'url': '../../images/blackChopper.jpg',
            // 'caption': 'Optional caption',
            // 'thumbUrl': '../../images/blackChopper.jpg' // used only for this example
            'thumbUrl': '../../images/blackChopper.jpg' // used only for this example
        },
        {
            'url': '../../images/blueChopper.jpg',
            'thumbUrl': '../../images/blueChopper.jpg'
        },
        {
            'url': '../../images/redChoper.jpg',
            'thumbUrl': '../../images/redChoper.jpg'
        }
    ];

    function openLightboxModal(index) {

        Lightbox.openModal(vm.images1, index);
        $templateCache.put('lightbox.html',
            "<div class=modal-body ng-swipe-left=Lightbox.nextImage() ng-swipe-right=Lightbox.prevImage()>" +
            "<div class=lightbox-nav><button class=close aria-hidden=true ng-click=$dismiss()>×" +
            "</button><div class=btn-group ng-if=\"Lightbox.images.length > 1\">" +
            "<a class=\"btn btn-xs btn-default\" ng-click=Lightbox.prevImage()>‹ Previous!!!!</a> " +
            "<a ng-href={{Lightbox.imageUrl}} target=_blank class=\"btn btn-xs btn-default\" title=\"Open in new tab\">Open image in new tab </a>" +
            " <a class=\"btn btn-xs btn-default\" ng-click=Lightbox.nextImage()>Next ›</a></div></div>" +
            "<div class=lightbox-image-container><div class=lightbox-image-caption><span>{{Lightbox.imageCaption}}</span></div>" +
            "<img ng-if=!Lightbox.isVideo(Lightbox.image) lightbox-src={{Lightbox.imageUrl}}>" +
            "<div ng-if=Lightbox.isVideo(Lightbox.image) class=\"embed-responsive embed-responsive-16by9\">" +
            "<video ng-if=!Lightbox.isSharedVideo(Lightbox.image) lightbox-src={{Lightbox.imageUrl}} controls autoplay>" +
            "</video><embed-video ng-if=Lightbox.isSharedVideo(Lightbox.image) lightbox-src={{Lightbox.imageUrl}}" +
            " ng-href={{Lightbox.imageUrl}} iframe-id=lightbox-video class=embed-responsive-item><a ng-href={{Lightbox.imageUrl}}>Watch video</a>" +
            "</embed-video></div></div></div>"
        );
    };


    vm.openLightboxModal = openLightboxModal
    vm.onLoad = onLoad
    vm.addImage = addImage;
    vm.getList = getList;
});
