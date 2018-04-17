app.factory('browserTitleService', function ($filter, $window) {

    var COMPANY_NAME = 'getRoomie';

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
        $window.document.title = COMPANY_NAME + ' | ' + (value);
    }

    function _setTitle(value) {
        $window.document.title = COMPANY_NAME + ' | ' + $filter('translate')(value);
    }

    function _setDefaultTitle() {
        $window.document.title = COMPANY_NAME;
    }

    return {
        setBrowserTitleByFilterName: setBrowserTitleByFilterName,
        setExplicitTitle: setExplicitTitle
    };

});