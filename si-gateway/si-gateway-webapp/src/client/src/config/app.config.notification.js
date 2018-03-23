"use strict";
app.factory('notify', function (ngNotify) {


    function notify() {
       return ngNotify.config({
           theme: 'pure',
           position: 'bottom',
           duration: 10000,
           sticky: false,
           button: false
       });
   }

   notify();

   return ngNotify;
});