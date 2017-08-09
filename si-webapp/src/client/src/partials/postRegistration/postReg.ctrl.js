app
    .controller("preSettingsController", function ($localStorage, postRegistrationModel, notify, $filter, $state) {

        var vm = this;
        vm.dataLoading = false;
        vm.isCollapsed = true;

        vm.preSettings = {};
        vm.country = $localStorage.countries;
        vm.cities = $localStorage.cities;
        vm.gender = $localStorage.genders;

        // if number of countries more one, use next line
        // vm.preSettings.country=vm.country[0].description
        vm.currCoutry = vm.country[0].description;

        function submitChanges() {
            vm.preSettings.genderId = findIdByDescription(vm.gender, vm.preSettings.genderId);
            vm.preSettings.cityId = findIdByDescription(vm.cities, vm.preSettings.cityId);
            vm.dataLoading = true;
            postRegistrationModel.saveDataPostRegistration(vm.preSettings).then(
                function (success) {
                    notify.set($filter('translate')('PostRegistration.message.save.success'), {type: 'success'});
                    vm.dataLoading = false;
                    $state.go('home');
                }, function (error) {
                    vm.dataLoading = false;
                    notify.set($filter('translate')('Error' + '.' + error.data.error.errorTypeCode), {type: 'error'});
                    if (error.data.error.errorTypeCode === 'AUT') {
                        $state.go('logout');
                    }
                });
        }

        function findIdByDescription(source, description) {
            var id;
            for (var i = 0; i < source.length; i++) {
                if (source[i].description === description) {
                    id = source[i].id;
                }
            }
            return id;
        }

        vm.submitChanges = submitChanges;


    });