/**
 * Created by evgeny on 1/1/2018.
 */

//http://www.advancesharp.com/blog/1194/angularjs-constant-and-enum-with-example
app.constant('accountStatus', {
    Statuses: {
        NEW: 1,
        CONFIRMED: 2,
        COMPLETED_USER_INFO: 3,
        COMPLETED_REQUIRED_MATCHING: 4,
        DEAL: 5,
        BLOCKED: -1
    }
});
