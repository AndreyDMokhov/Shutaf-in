app.controller('userProfilePage', function ($state, $filter, sessionService, $sessionStorage) {
    var vm = this;
    vm.userProfile = $sessionStorage.userProfile;
});