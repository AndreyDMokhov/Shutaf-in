"use strict";
app.factory('dealModel', function () {

    var pallet =
        [
            [
                {
                    panelId: 0,
                    title: "Bills",
                    documents: [
                        {
                            documentId: 0,
                            title: 'Bill',
                            type: 1,
                            createdDate: 12345678901234
                        },
                        {
                            documentId: 1,
                            title: 'Bill2',
                            type: 1,
                            createdDate: 15345678901234
                        }
                    ]
                },
                {
                    panelId: 1,
                    title: "Photos",
                    documents: [
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
                        }]
                }
            ],
            [
                {
                    panelId: 0,
                    title: "BillsA",
                    documents: [
                        {
                            documentId: 0,
                            title: 'BillA',
                            type: 1,
                            createdDate: 12345678901234
                        },
                        {
                            documentId: 1,
                            title: 'BillA1',
                            type: 1,
                            createdDate: 15345678901234
                        }
                    ]
                },
                {
                    panelId: 1,
                    title: "PhotosA",
                    documents: [
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
                        }
                    ]
                }
            ]
        ];

    function getPanels(dealId) {
        return pallet[dealId];
    }

    function addPallet(dealId, namePallet) {

        pallet[dealId].push(
            {
                panelId: getRandomInt(0, 10000000000),
                title: namePallet,
                documents: []
            });
        return pallet;
    }

    function getRandomInt(min, max) {
        return Math.floor(Math.random() * (max - min)) + min;
    }

    return {
        addPallet: addPallet,
        getPanels: getPanels
    };
});