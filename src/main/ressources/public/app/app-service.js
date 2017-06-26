/* 
 * Gesion de la liste des adhérents
 */
okeanosApp.service('sharedProperties', [function () {

    var usersList = [{
        "lastname": "Rouvier ",
        "firstname": "Baptiste",
        "telNumber": "0663154059",
        "birthday": "1985-12-12T23:00:00.000Z",
        "licenceNumber": "A-1985-13",
        "licenceType": "Adulte",
        "membershipType": "hockey",
        "assurance": true,
        "team": "Equipe 2"
    }];

    var currentEditedUser = {};

    this.UpdateUsersList = function (newObj) {
        usersList.push(angular.copy(newObj));
    };
    this.getUsersList = function () {
        return usersList;
    };
    this.clearAll = function () {
        usersList.length = 0;
    };

    this.setCurrentEditedUser = function (user) {
        currentEditedUser = angular.copy(user);
    };

    this.getCurrentEditedUser = function () {
        return currentEditedUser;
    };
}]);

/* 
 * Gestion de l'utilisateur courant
 */
okeanosApp.service('securityService', ['$location', function ($location) {
    console.log('Init service securityService');
    var security = {
        isLogin: "false",
        isAdmin: "false"
    };

    this.setSecurity = function (login, admin) {
        security.isLogin = login;
        security.isAdmin = admin;
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
            $location.path('/userForm');
        };
    };
}]);

/* 
 * Gesion des comptes utilisteurs
 */
okeanosApp.factory('Account', function ($resource) {
    return $resource(okeanoAppUrl + 'account/:id'); // Note the full endpoint address
});

/* 
 * Gesion des types de formation de plongée
 */
okeanosApp.factory('DivingTraining', function ($resource) {
    return $resource(okeanoAppUrl + 'diving_training/:id'); // Note the full endpoint address
});

/* 
 * Gesion des types d'adhésions
 */
okeanosApp.factory('SubscriptionType', function ($resource) {
    return $resource(okeanoAppUrl + 'subscription_type/:id'); // Note the full endpoint address
});
