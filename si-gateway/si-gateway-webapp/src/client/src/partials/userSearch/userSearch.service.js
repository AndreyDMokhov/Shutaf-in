app.factory('userSearchService', function () {
    var callbackSearchList=[];
    var searchList = '';

    var addSearchList = function (value) {
        searchList=value;
        var i=0;
        for(i=0; i<callbackSearchList.length;i++)
            callbackSearchList[i](searchList);
    };
    var onSearchListRefreshed=function(value){
        callbackSearchList.push(value);
    };
    return{
        addSearchList:addSearchList,
        onSearchListRefreshed:onSearchListRefreshed
    };
});