app.factory('browserTitle', function ($filter, $window) {


    function setBrowserTitleByFilterName(filterName) {
        if ($filter === null || $filter === undefined
            || filterName === null || filterName === undefined || filterName === '') {

            _setDefaultTitle();
        } else {
            _setTitle(filterName);
        }
    }

    function setExplicitTitle(value) {
        if (value === undefined || value === null || value === '') {
            _setDefaultTitle();
        }
        $window.document.title = 'Shutaf-In' + ' | ' + (value);
    }

    function _setTitle(value) {
        $window.document.title = 'Shutaf-In' + ' | ' + $filter('translate')(value);
    }

    function _setDefaultTitle() {
        $window.document.title = 'Shutaf-In';
    }

    return {
        setBrowserTitleByFilterName: setBrowserTitleByFilterName,
        setExplicitTitle: setExplicitTitle
    };

});