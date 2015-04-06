'use strict';

angular.module('demoApp')
    .controller('UsersController', function ($scope, User) {
        $scope.users = [];
        $scope.loadAll = function() {
            User.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.users = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();
    });
