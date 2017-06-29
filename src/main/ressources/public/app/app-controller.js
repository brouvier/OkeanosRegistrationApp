var okeanosAppControllers = angular.module('okeanosAppControllers', []);

/* 
 * Contrôleur des données de connexion
 */
okeanosAppControllers.controller('loginCtrl', function ($scope, $location, $http, securityService) {
    console.log('Init controler loginCtrl');
    $scope.security = securityService.getSecurity();
    $scope.alerte = "none";

    $scope.login = function () {
        $scope.alerte = "none";
        console.log("$scope.login.mode : " + $scope.login.mode);

        if ($scope.login.mode == "Inscription") {
            $http.get(okeanoAppUrl + 'security/signup/' + $scope.login.email + '/' + $scope.login.password)
                .then(function (response) {
                    console.log('Sign up response = ' + response.data);
                });
        } else {
            $scope.alerte = "Tenetative de connexion";
            $http.get(okeanoAppUrl + 'security/login/' + $scope.login.email + '/' + $scope.login.password)
                .then(function (response) {
                    console.log('Login response = ' + response.data);
                    if (response.data == 'true') {
                        $scope.alerte = "Connection succed";
                        $location.path("userForm");
                    } else {
                        $scope.alerte = "Erreur de connexion !";
                    }
                });
        }

    };

    $scope.logout = function () {
        $http.get(okeanoAppUrl + 'security/logout')
            .then(function (response) {
                console.log(response);
                if (response.data == 'true') {
                    $location.path("userLogin");
                } else {
                    console.log('Erreur de déconnexion !!!');
                }
            });
    };
});

/* 
 * Contrôleur d'ajout d'adhérents
 */
okeanosAppControllers.controller('adherentFormCtrl', function ($scope, $location, $http, securityService, sharedProperties) {
    securityService.checkIsLogin();

    $scope.updateState = '';
    $scope.adherent = sharedProperties.getCurrentEditedUser();

    if ($scope.adherent.birthday) {
        $scope.adherent.birthday = new Date($scope.adherent.birthday);
    };

    $scope.update = function (adherent) {
        $scope.updateState = 'wip';
        sharedProperties.setCurrentEditedUser(adherent);
        sharedProperties.UpdateUsersList(adherent);

        $http
            .post(okeanoAppUrl + 'adherent_info', adherent)
            .then(function (response) {
                console.log(response);
                if (response.status == 200) {
                    $scope.updateState = 'done';
                } else {
                    $scope.updateState = 'fail';
                }
            });


    };
});

/* 
 * Contrôleur de la liste des utilisateurs
 */
okeanosAppControllers.controller('listUsrCtrl', function ($scope, $location, securityService, sharedProperties) {
    securityService.checkIsAdmin();

    // initialisation
    $scope.users = sharedProperties.getUsersList();

    $scope.gotoEditForm = function () {
        sharedProperties.setCurrentEditedUser(this.user);
        $location.path("userForm");
    };
});



/* 
 * Contrôleur de la liste des comptes utilisateurs
 */
okeanosAppControllers.controller('accountListCtrl', function ($scope, securityService, Account) {
    securityService.checkIsLogin();

    $scope.accounts = Account.query();

    $scope.loadEdit = function (account) {
        $scope.newAccountId = account.id;
        $scope.newAccountMail = account.mail;
        $scope.newAccountAdmin = account.admin;
    };

    $scope.saveEdit = function () {
        var account = new Account();
        account.id = $scope.newAccountId;
        account.mail = $scope.newAccountMail;
        account.admin = $scope.newAccountAdmin;
        account.$save();

        $scope.refreshList();
    };

    $scope.remove = function (account) {
        console.log('Suppression du compte : ' + account.mail);
        $scope.accounts = [];
        Account.delete(account);

        $scope.refreshList();
    };

    $scope.refreshList = function () {
        $scope.subscriptionTypeList = [];
        $scope.newAccountId = null;
        $scope.newAccountMail = null;
        $scope.newAccountAdmin = null;
        $scope.accounts = Account.query();
    };
});


/* 
 * Contrôleur de la liste des types d'adhésions
 */
okeanosAppControllers.controller('subscriptionTypeCtrl', function ($scope, $location, $http, securityService, SubscriptionType) {
    securityService.checkIsAdmin();

    console.log('Appel de la liste des type de souscription');
    $scope.refreshSubscriptionType();

    $scope.createSubscriptionType = function () {
        console.log('Création d un element');
        var sType = new SubscriptionType();
        sType.id = $scope.newSTypeId;
        sType.label = $scope.newSTypeLabel;
        sType.$save();

        $scope.refreshSubscriptionType();
    };

    $scope.editSubscriptionType = function (sType) {
        console.log('Edition d un element');
        $scope.newSTypeId = sType.id;
        $scope.newSTypeLabel = sType.label;
    };

    $scope.removeSubscriptionType = function (sType) {
        $scope.subscriptionTypeList = [];
        SubscriptionType.delete(sType);

        $scope.refreshSubscriptionType();
    };

    $scope.refreshSubscriptionType = function () {
        $scope.newSTypeId = null;
        $scope.newSTypeLabel = null;
        $scope.subscriptionTypeList = SubscriptionType.query();
    };


});
