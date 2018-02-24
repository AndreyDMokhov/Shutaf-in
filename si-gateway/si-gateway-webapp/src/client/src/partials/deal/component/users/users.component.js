app.component('usersDealComponent', {
    templateUrl: "partials/deal/component/users/users.component.html",
    bindings: {
        usersInfo: '<'
    },
    controllerAs: 'vm',
    controller: function () {
        var vm = this;
        var limitUsersForShow = 2;
        var lastElement=0;
        vm.partUsersInfo = [];
        vm.arrowUp = false;
        vm.arrowDown = false;
        vm.arrows = false;

        vm.$onInit = function () {
            var i;
            if(vm.usersInfo.length <= limitUsersForShow){
              vm.partUsersInfo = vm.usersInfo;
            } else {
                for ( i=0; i<limitUsersForShow; i++) {
                    vm.partUsersInfo[i] = vm.usersInfo[i];
                }
                vm.arrows =true;
                vm.arrowDown = true;
                lastElement = i;
            }
        };

        vm.downArrow = function () {
            debugger;
            if(lastElement === vm.usersInfo.length){
                return;
            }
            var i = lastElement;
            var j=0;
            lastElement = lastElement + limitUsersForShow;
            vm.partUsersInfo = [];
            for(; i < lastElement && i < vm.usersInfo.length; i++){
                vm.partUsersInfo[j++] = vm.usersInfo[i];

            }

            if(i == vm.usersInfo.length  ){
                vm.arrowDown = false;
            }
            lastElement = i;
            vm.arrowUp = true;

        };

        vm.upArrow = function () {

            var j = 0;

            var previousElement = lastElement - limitUsersForShow-1;

            if (previousElement<0) previousElement = 0;

            for (var i = previousElement; i < lastElement-1; i++){
                vm.partUsersInfo[j++] = vm.usersInfo[i];
            }

            if (previousElement === 0) vm.arrowUp = false;
            vm.arrowDown = true;
            lastElement = limitUsersForShow;
        };

        function getUserImage(img) {
            if (!img) {
                return '../../images/default_avatar.png';
            }
            else {
                return 'data:image/jpeg;base64,' + img;
            }
        }
        vm.getUserImage = getUserImage;
    }



});