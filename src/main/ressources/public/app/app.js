'use strict';

var okeanosApp = angular.module('okeanosApp', [
    // Dépendances du "module"
    'ngRoute', 'ngResource', 'ngFileUpload', 'ngTableToCsv',
    'okeanosAppControllers'
]);

/**
 * Configuration du module principal : routeApp
 */
okeanosApp.config(['$routeProvider', '$httpProvider',
    function ($routeProvider, $httpProvider) {

        // Système de routage
        $routeProvider
            .when('/userLogin', {
                templateUrl: 'partials/userLogin.html',
                controller: 'loginCtrl'
            })
            .when('/dashboard', {
                templateUrl: 'partials/dashboard.html',
                controller: 'dashboardCtrl',
                authorized: true
            })
            .when('/adherentInfo', {
                templateUrl: 'partials/adherent_info.html',
                controller: 'adherentInfoCtrl',
                authorized: true
            })
            .when('/adherentInfoSaison', {
                templateUrl: 'partials/adherent_info_saison.html',
                controller: 'adherentInfoSaisonCtrl',
                authorized: true
            })
            .when('/adherentInfoSaisonList', {
                templateUrl: 'partials/adherent_info_saison_list.html',
                controller: 'adherentInfoSaisonListCtrl',
                authorized: true
            })
            .when('/accountList', {
                templateUrl: 'partials/accountList.html',
                controller: 'accountListCtrl',
                authorized: true
            })
            .when('/saison', {
                templateUrl: 'partials/saisonList.html',
                controller: 'saisonListCtrl',
                authorized: true
            })
            .when('/divingTraining', {
                templateUrl: 'partials/diving_training.html',
                controller: 'divingTrainingCtrl',
                authorized: true
            })
            .when('/hockeyTeam', {
                templateUrl: 'partials/hockey_team.html',
                controller: 'hockeyTeamCtrl',
                authorized: true
            })
            .when('/subscription', {
                templateUrl: 'partials/subscription.html',
                controller: 'subscriptionCtrl',
                authorized: true
            })
            .when('/ffessmLicence', {
                templateUrl: 'partials/ffessm_licence.html',
                controller: 'ffessmLicenceCtrl',
                authorized: true
            })
            .when('/insurance', {
                templateUrl: 'partials/insurance.html',
                controller: 'insuranceCtrl',
                authorized: true
            })
            .when('/passwordRequest', {
                templateUrl: 'partials/userPassRequest.html',
                controller: 'passwordCtrl',
                authorized: true
            })
            .when('/passwordUpdate/:ticket', {
                templateUrl: 'partials/userPassUpdate.html',
                controller: 'passwordCtrl',
                authorized: true
            })
            .when('/sickNote', {
                templateUrl: 'partials/sickNoteUpload.html',
                controller: 'sickNoteUploadCtrl',
                authorized: true
            })
            .when('/teamBinder', {
                templateUrl: 'partials/hockey_team_binder.html',
                controller: 'hockeyTeamBinderCtrl',
                authorized: true
            })
            .otherwise({
                redirectTo: '/dashboard'
            });

        // Allow credential for CORS request
        $httpProvider.defaults.withCredentials = true;
        $httpProvider.defaults.useXDomain = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];

        //register the interceptor as a service
        $httpProvider.interceptors.push(function ($location, securityService) {
            return {
                'response': function (response) {
                    if (response.config.url.startsWith(okeanoAppUrl)) {
                        //Do your custom processing here
                        // do something on success
                        //console.log('myHttpInterceptor response : ' + response.config.url);
                        //console.log('okeanoAppUrl startsWith ', okeanoAppUrl, response);
                        securityService.setSecurity(response.headers('isLogin'), response.headers('isAdmin'), response.headers('curentAccountId'));
                        //console.log(securityService.getSecurity());
                    } else console.log('Url startsWith ', okeanoAppUrl, response);

                    return response;
                },
                'responseError': function (rejection) {
                    console.log('responseError :', rejection);
                    if (rejection.status === 401) {
                        securityService.setSecurity(false, false, null);
                        $location.url('/userLogin');
                    }
                }
            };
        });

    }
]);

okeanosApp.run(function ($rootScope, $location, $window) {
    $rootScope.$on('$routeChangeSuccess', function () {
        //console.log($location);

        if (okeanoAppUrl.includes('https') && $location.protocol() !== 'https') {
            $window.location.href = $location.absUrl().replace('http', 'https');
        };
    });
});