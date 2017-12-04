app.factory('palletModel', function () {

    var documents = [
        [
            {
                documentId: 0,
                title: 'Bill',
                type: 1,
                createdDate: 12345678901234
            },
            {
                documentId: 1,
                title: 'Bill',
                type: 1,
                createdDate: 15345678901234
            },
            [
                {
                    documentId: 3,
                    title: 'Photo',
                    type: 2,
                    createdDate: 12345678901234
                },
                {
                    documentId: 4,
                    title: 'Photo2',
                    type: 1,
                    createdDate: 12345678901234
                },
            ]
        ],
    ];

    function getDocuments() {
        return documents;
    }

    return {
        getDocuments: getDocuments
    };
});
