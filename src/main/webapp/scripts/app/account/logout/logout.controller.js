'use strict';

angular.module('demoApp')
    .controller('LogoutController', function ( $state, Auth) {
        Auth.logout();
        $state.go('login');
    });
