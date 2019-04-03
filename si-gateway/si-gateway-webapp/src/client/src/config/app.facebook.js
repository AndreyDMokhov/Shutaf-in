app.run(function($rootScope, $window, authenticationService) {

        $rootScope.user = {};

        $window.fbAsyncInit = function() {
            // Executed when the SDK is loaded
            debugger;
            FB.init({

                /*
                 The app id of the web app;
                 To register a new app visit Facebook App Dashboard
                 ( https://developers.facebook.com/apps/ )
                */

                appId: '116441945825716',

                /*
                 Adding a Channel File improves the performance
                 of the javascript SDK, by addressing issues
                 with cross-domain communication in certain browsers.
                */

                channelUrl: 'http://138.68.105.68:8080/#/home',

                /*
                 Set if you want to check the authentication status
                 at the start up of the app
                */

                status: true,

                /*
                 Enable cookies to allow the server to access
                 the session
                */

                cookie: true,

                /* Parse XFBML */

                xfbml: true,

                version: 'v3.0'
            });


            authenticationService.watchLoginChange();
        };

       (function(d){
            // load the Facebook javascript SDK

            var js,
                id = 'facebook-jssdk',
                ref = d.getElementsByTagName('script')[0];

            if (d.getElementById(id)) {
                return;
            }

            js = d.createElement('script');
            js.id = id;
            js.async = true;
            js.src = "bower_components/FS_SDK/sdk.js";

            ref.parentNode.insertBefore(js, ref);

        }(document));

    });