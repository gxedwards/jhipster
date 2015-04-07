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
                        controller: 'UsersController as userVM'
                    }
                }

            })
            .state('userDetail', {
                parent: 'admin',
                url: '/users/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'User Detail'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/users/users-detail.html',
                        controller: 'UsersDetailController'
                    }
                }
            });

    });
