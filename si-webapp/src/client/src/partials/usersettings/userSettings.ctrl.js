app.controller('userSettingsController', function (userSettingsModel, userInitService, languageService, constantService, $filter, notify) {
    var vm = this;
    vm.dataLoading = false;

    vm.accountSettings = {};
    vm.languages = {};
    vm.languageSelect;


    function activate() {
        vm.languages = constantService.getLanguages();
        getCurrentUserData();
    }

    function getCurrentUserData() {

        userSettingsModel.getCurrentUserData().then(
            function (success) {
                vm.accountSettings = success;
                getCurrentLanguageById();
            }, function (error) {
                console.log(error);
            });
    }

    function getCurrentLanguageById(){
        vm.languageSelect=vm.languages.filter(function(a){ return a.id === vm.accountSettings.languageId })[0];
    }

    function saveNewUserData() {
        vm.dataLoading = true;
        vm.accountSettings.languageId =  vm.languageSelect.id;
            userSettingsModel.saveNewUserData(vm.accountSettings).then(
                function (success) {
                    vm.dataLoading = false;

                    notify.set($filter('translate')('UserSettings.message.save.success'), {type: 'success'});
                    updateAllData();
                }, function (error) {
                    vm.dataLoading = false;

                    notify.set($filter('translate')('UserSettings.message.save.fail'), {type: 'error'});
                    console.log(error);
                });
    }

    function updateAllData(){
        languageService.updateUserLanguage({"id" : vm.languageSelect.id, "description" : vm.languageSelect.description});
        userInitService.init();
        getCurrentUserData();
    }

    activate();

    vm.saveNewUserData = saveNewUserData;
    vm.getCurrentUserData = getCurrentUserData;

});