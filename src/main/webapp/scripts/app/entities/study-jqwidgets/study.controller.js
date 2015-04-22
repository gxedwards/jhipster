'use strict';

angular.module('demoApp')
    .controller('StudyJQWidgetsController', function ($scope, Study, ParseLinks) {

        $scope.createGrid = false;

        $scope.loadAll = function() {
            Study.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.studys = result;

                $scope.source =
                {
                    datatype: "json",
                    localdata: result
                };

                // grid settings
                $scope.gridSettings =
                {
                    source: $scope.source,
                    width: "100%",
                    columns: [
                        { text: 'ID', datafield: 'id', width: 120 },
                        { text: 'Study Name', datafield: 'study_name' }            ]
                };


                $scope.createGrid = true;

            });
        };
        $scope.loadAll();

        $scope.columns =
            [
                { text: 'ID', datafield: 'id', width: 120 },
                { text: 'Study Name', datafield: 'study_name' }
            ];
    });
