app.controller('userProfilePage', function ($state, $filter, sessionService) {
    var vm = this;
    vm.userProfile = JSON.parse(sessionStorage.getItem('userProfile'));
});