'use strict';

var config = {  
    propertiesUrl: "properties.js",
    serverUp: true,
    modeDebug: false,
    okeanoAppUrl: "undefine"
}; 

// Contrôle de la disponibilité du serveur
var xmlHttp = new XMLHttpRequest();
xmlHttp.open( "GET", config.propertiesUrl, false ); // false for synchronous request
xmlHttp.send( null );
console.log("xmlHttp", xmlHttp);
if(xmlHttp.status === 404){
    config.serverUp = false;
    console.log("Serveur indisponible", config);
} else {
    console.log("Chargement du fichier de paramètre");
    var prop = JSON.parse(xmlHttp.responseText);
    config.modeDebug = prop.modeDebug;
    config.okeanoAppUrl = prop.okeanoAppUrl;
}

/////////////////////////////////////////////////////////////////////////////////////////////////////

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
                redirectTo: '/userLogin'
            });

        // Allow credential for CORS request
        $httpProvider.defaults.withCredentials = true;
        $httpProvider.defaults.useXDomain = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];

        // Contrôle de la disponibilité du serveur
        if(!config.serverUp){
            config.serverUp = false;
            console.log("Serveur indisponible", config);
            $routeProvider.when('/500', {
                templateUrl: 'partials/500.html'
                    }).otherwise({
                        redirectTo: '/500'
                    });
            return;
        }

        //register the interceptor as a service
        $httpProvider.interceptors.push(function ($location, securityService) {
            return {
                'response': function (response) {
                    if (response.config.url.startsWith(config.okeanoAppUrl)) {
                        //Do your custom processing here do something on success
                        //console.log('myHttpInterceptor response : ' + response.config.url);
                        //console.log('okeanoAppUrl startsWith ', config.okeanoAppUrl, response);
                        securityService.setSecurity(response.headers('isLogin'), response.headers('isAdmin'), response.headers('curentAccountId'));
                        //console.log(securityService.getSecurity());
                    };
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

        if (config.serverUp && config.okeanoAppUrl.includes('https') && $location.protocol() !== 'https') {
            $window.location.href = $location.absUrl().replace('http', 'https');
        };
    });
});