'use strict';

angular.module('demoApp')
    .controller('UsersDetailController', function ($scope, $stateParams, User) {
        $scope.user = {};
        $scope.load = function (id) {
            User.get({login: id}, function(result) {
              $scope.user = result;
            });
        };
        $scope.load($stateParams.id);
    });
