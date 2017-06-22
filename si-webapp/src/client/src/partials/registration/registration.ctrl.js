app.controller('userRegistration', function (registrationModel) {
    var vm = this;

    vm.registrationData = {}

    function registerUser() {
        console.log(vm.registrationData);
        registrationModel.registerUser(vm.registrationData).then(
    function (success) {
        alert('Registration is ok');

    },function (error) {
                alert("Registration fail");
                console.log(error);
            }
        )
    }

    vm.registerUser = registerUser;
});

app.directive("matchPassword", function () {
    return {
        require: "ngModel",
        scope: {
            otherModelValue: "=matchPassword"
        },
        link: function(scope, element, attributes, ngModel) {

            ngModel.$validators.matchPassword12 = function(modelValue) {
                return modelValue == scope.otherModelValue;
            };

            scope.$watch("otherModelValue", function() {
                ngModel.$validate();
            });
        }
    };
});