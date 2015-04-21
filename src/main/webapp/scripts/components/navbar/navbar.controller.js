'use strict';

angular.module('demoApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.isInRole = Principal.isInRole;
        $scope.$state = $state;

        Principal.identity().then(function(account) {
            $scope.account = account;
        });

        $scope.logout = function () {
            Auth.logout();
            $state.go('login');
        };
    });
