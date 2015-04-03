'use strict';

angular.module('demoApp')
    .controller('StudyController', function ($scope, Study, ParseLinks) {
        $scope.studys = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Study.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.studys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Study.update($scope.study,
                function () {
                    $scope.loadAll();
                    $('#saveStudyModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Study.get({id: id}, function(result) {
                $scope.study = result;
                $('#saveStudyModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Study.get({id: id}, function(result) {
                $scope.study = result;
                $('#deleteStudyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Study.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteStudyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.study = {study_name: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
