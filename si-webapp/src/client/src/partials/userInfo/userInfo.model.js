app.factory("userInfoModel", function (Restangular, $sessionStorage) {
  var rest = Restangular.withConfig(function (RestangularProvider) {
      RestangularProvider.setFullResponse(true);
      RestangularProvider.setBaseUrl("/api/users/info");
  });

function saveDataPostRegistration(params) {
    rest.setDefaultHeaders({'session_id':$sessionStorage.sessionId});
    return rest.one('/').customPOST(params)
}

return{
    saveDataPostRegistration: saveDataPostRegistration
}
});

