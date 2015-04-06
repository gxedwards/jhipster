'use strict';

angular.module('demoApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('users', {
                parent: 'admin',
                url: '/users',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'Users'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/users/users.html',
                        controller: 'UsersController'
                    }
                }

            });
    });
