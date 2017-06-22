app.config(function ($stateProvider, $urlRouterProvider) {
    $stateProvider
        .state("home",
            {
                templateUrl: "partials/home/home.html",
                controller: "homeController",
                controllerAs: "vm",
                url: "/home"
            })
        .state("about",
            {
                templateUrl: "partials/about/about.html",
                controller: "aboutController",
                controllerAs: "vm",
                url: "/about"
            })
        .state("users",
            {
                templateUrl: "partials/users/users.html",
                controller: "usersController",
                controllerAs: "vm",
                url: "/users"
            });
    $urlRouterProvider.otherwise("/home");
});