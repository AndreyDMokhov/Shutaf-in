app.factory('uiNotification', function (notify) {

    var types = [
        {type: 'warn', classType: 'alert-warning'},
        {type: 'success', classType: 'alert-success'},
        {type: 'error', classType: 'btn-danger'},
        {type: 'info', classType: 'btn-info'}
    ];


    function show(text, type, isHtml) {
        if (text === undefined) {
            text = 'undefined';
        }
        var typeObject = _resolveType(type);

        if (isHtml) {
            return notify({messageTemplate: text, classes: [typeObject.classType]});
        }

        return notify({messageTemplate: '<h4>' + text + '</h4>', classes: [typeObject.classType]});
    }

    function _resolveType(type) {
        var retVal = {type: 'info', classType: 'btn-info'};
        if (type === undefined || type === null || type === '') {
            return retVal;
        }

        for (var i = 0; i < types.length; i++) {
            if (types[i].type === type) {
                return types[i];
            }
        }

        return retVal;

    }

    return {
        show: show
    };
});