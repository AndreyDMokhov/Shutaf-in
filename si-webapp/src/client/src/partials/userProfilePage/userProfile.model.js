app.factory('userProfileModel', function (Restangular, CACHED_USER_IMAGE_ID, $q) {

    var rest = Restangular.withConfig(function (RestangularProvider) {
        RestangularProvider.setFullResponse(true);
        RestangularProvider.setBaseUrl('/api/images');
    });

    function getImage(params) {
        rest.setDefaultHeaders({'session_id': localStorage.getItem('session_id')});
        return rest.one('/').customGET(params);

    }

    function addImage(params) {
        rest.setDefaultHeaders({'session_id': localStorage.getItem('session_id')});
        return rest.one('/').customPOST(params)
    }


    function getDataImage() {
        rest.setDefaultHeaders({'session_id': localStorage.getItem('session_id')});
        var imageId = JSON.parse(localStorage.getItem(CACHED_USER_IMAGE_ID));
        console.log(imageId)
        if(imageId == null) return null
        else{
        data =  rest.one('/').customGET(imageId.imageId);
        var deferred = $q.defer();
        data.get().then(
            function (success) {
                deferred.resolve(data);
            },
            function (error) {
                return deferred.reject();
            }
        )
        return deferred.promise;
        }
    }

    function getAllImages() {
        rest.setDefaultHeaders({'session_id': localStorage.getItem('session_id')});

    }


    return {
        getImage: getImage,
        addImage: addImage,
        getDataImage:getDataImage
    }
})
