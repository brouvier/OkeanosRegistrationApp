/* 
 * Contexte de l'utilisateur courant et de ses droits
 */
okeanosApp.service('securityService', ['$location', function ($location) {
    console.log('Init service securityService');
    var security = {
        isLogin: "false",
        isAdmin: "false",
        curentAccountId: null
    };

    this.setSecurity = function (login, admin, id) {
        security.isLogin = login;
        security.isAdmin = admin;
        security.curentAccountId = id;
    };

    this.getSecurity = function () {
        return security;
    };

    this.checkIsLogin = function () {
        if (security.isLogin != "true") {
            $location.path('/userLogin');
        };
    };

    this.checkIsAdmin = function () {
        this.checkIsLogin();
        if (security.isAdmin != "true") {
            return false;
        }
        return true;
    };
}]);

/* 
 * Contexte des filtres de l'utilisateur
 */
okeanosApp.service('filterService', [function () {
    console.log('Init service filterService');

    this.modeVisu = 'validation';
    this.modeFiltre = "all";
    this.subscriptionIdFilter = 0;
}]);

/* 
 * Gesion des comptes utilisteurs
 */
okeanosApp.factory('Account', function ($resource) {
    return $resource(okeanoAppUrl + 'account/:id'); // Note the full endpoint address
});

/* 
 * Gesion des informations adherent
 */
okeanosApp.factory('AdherentInfo', function ($resource) {
    return $resource(okeanoAppUrl + 'adherent_info/:id');
});

/* 
 * Gesion des informations adherent pour la saison
 */
okeanosApp.factory('AdherentInfoSaison', function ($resource) {
    return $resource(okeanoAppUrl + 'adherent_info_saison/:id');
});

/* 
 * Gesion des comptes utilisteurs
 */
okeanosApp.factory('Saison', function ($resource) {
    return $resource(okeanoAppUrl + 'saison/:id');
});

/* 
 * Gesion des types de formation de plongée
 */
okeanosApp.factory('DivingTraining', function ($resource) {
    return $resource(okeanoAppUrl + 'diving_training/:id');
});

/* 
 * Gesion des équipes de hockey
 */
okeanosApp.factory('HockeyTeam', function ($resource) {
    return $resource(okeanoAppUrl + 'hockey_team/:id');
});

/* 
 * Gesion des tarifs d'adhésions
 */
okeanosApp.factory('Subscription', function ($resource) {
    return $resource(okeanoAppUrl + 'subscription/:id');
});

/* 
 * Gesion des types d'adhésions
 */
okeanosApp.factory('SubscriptionType', function ($resource) {
    return $resource(okeanoAppUrl + 'subscription_type/:id'); // Note the full endpoint address
});

/* 
 * Gesion des licences ffessm
 */
okeanosApp.factory('FfessmLicence', function ($resource) {
    return $resource(okeanoAppUrl + 'ffessm_licence/:id');
});

/* 
 * Gesion des assurances
 */
okeanosApp.factory('Insurance', function ($resource) {
    return $resource(okeanoAppUrl + 'insurance/:id');
});
