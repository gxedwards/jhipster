'use strict';
/*
 This is using the controller As syntax so we are not embedding the $scope
 */
angular.module('demoApp')
    .controller('UsersController', function (User) {
        var vm = this;
        vm.users = [];
        vm.loadAll = function() {
            User.query({page: vm.page, per_page: 20}, function(result, headers) {
                vm.users = result;
            });
        };
        vm.loadPage = function(page) {
            vm.page = page;
            vm.loadAll();
        };
        vm.loadAll();
    });
