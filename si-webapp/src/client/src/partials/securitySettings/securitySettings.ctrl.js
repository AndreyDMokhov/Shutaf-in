app.controller('securitySettingsController', function (securitySettingsModel) {

    var vm = this;

    vm.users = {};

    function activate() {
        getUserData();
    }

    function getUserData() {

        securitySettingsModel.getUser().then(
            function (success) {
                vm.users = success;
            }, function (error) {
                console.log(error);
            });
    }
    function getEmail() {

        securitySettingsModel.getEmail().then(
            function (success) {
                getUserData();
                vm.users = success;
            }, function (error) {
                console.log(error);
            });
    }

    function updateEmail() {

        securitySettingsModel.updateEmail().then(
            function (success) {
                var email = getEmail();

               // vm.users = success;
                function formatEmail(currentEmail){
                    var splitEmail = currentEmail.split("@");
                    var domain = splitEmail[1];
                    var name = splitEmail[0];
                    return name.substring(0,3).concat(("*********@").concat(domain))
                }
               formatEmail(email);

            }, function (error) {
                console.log(error);
            });

    }
    function updatePassword() {

        securitySettingsModel.updatePassword().then(
            function (success) {
                getUserData();
               // vm.users = success;
            }, function (error) {
                console.log(error);
            });
    }
    activate();

      vm.updateEmail = updateEmail;
      vm.updatePassword = updatePassword;
      vm.getUserData = getUserData;

    });