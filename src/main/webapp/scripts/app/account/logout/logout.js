'use strict';

angular.module('demoApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('logout', {
                parent: 'account',
                url: '/logout',
                data: {
                    roles: []
                },
                controller: 'LogoutController'
            });
    });
