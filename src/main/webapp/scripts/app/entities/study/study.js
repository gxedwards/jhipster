'use strict';

angular.module('demoApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('study', {
                parent: 'entity',
                url: '/study',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'demoApp.study.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/study/studys.html',
                        controller: 'StudyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('study');
                        return $translate.refresh();
                    }]
                }
            })
            .state('studyDetail', {
                parent: 'entity',
                url: '/study/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'demoApp.study.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/study/study-detail.html',
                        controller: 'StudyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('study');
                        return $translate.refresh();
                    }]
                }
            });
    });
