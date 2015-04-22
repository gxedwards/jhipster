'use strict';

angular.module('demoApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('studyJQWidgets', {
                parent: 'entity',
                url: '/studyJQWidgets',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'demoApp.study.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/study-jqwidgets/studys.html',
                        controller: 'StudyJQWidgetsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('study');
                        return $translate.refresh();
                    }]
                }
            }) ;
    });
