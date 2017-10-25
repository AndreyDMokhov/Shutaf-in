app.controller('settingsController', function ($filter, $state,$window) {
    var vm = this;

    vm.tabs = [
        {
            tabId: 1,
            title: $filter('translate')('UserSettings.personal.title'),
            state: 'settings.personal',
            icon: 'fa-info'
        },
        {
            tabId: 2,
            title: $filter('translate')('Settings.security.email.title'),
            state: 'settings.changeEmailRequest',
            icon: 'fa-envelope'
        },
        {
            tabId: 3,
            title: $filter('translate')('Settings.security.password.title'),
            state: 'settings.changePassword',
            icon: 'fa-key'
        }
    ];

    vm.getCurrentStateName = function() {
        return $state.$current.name;
    };

    vm.selectedTab = function (tab) {
        $state.go(tab.state);
    };


});