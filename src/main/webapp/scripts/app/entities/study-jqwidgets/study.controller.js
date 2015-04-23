'use strict';

angular.module('demoApp')
    .controller('StudyJQWidgetsController', function ($scope, Study, ParseLinks) {

        $scope.createGrid = true;

        $scope.loadAll = function() {
            Study.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.studys = result;

                // used for the second grid
                $scope.source.localdata = result;
                $scope.dataAdapter.dataBind();

            });
        };
        $scope.loadAll();

        $scope.columns =
            [
                { text: 'ID', datafield: 'id', width: 120 },
                { text: 'Study Name', datafield: 'study_name' }
            ];

        $scope.source =
        {
            datatype: "local",
            localdata: []
        };

        $scope.dataAdapter = new $.jqx.dataAdapter($scope.source);
        // grid settings
        $scope.gridSettings =
        {
            source: $scope.dataAdapter,
            width: "100%",
            theme: 'metrodark',
            columns: [
                { text: 'ID', datafield: 'id', width: 120 },
                { text: 'Study Name', datafield: 'study_name' }            ]
        };


        $scope.createGrid = true;

    });
