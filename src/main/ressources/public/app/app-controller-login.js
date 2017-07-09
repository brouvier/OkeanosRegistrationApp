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
            $scope.alerte = "Tentative de connexion";
            $http.get(okeanoAppUrl + 'security/login/' + $scope.login.email + '/' + $scope.login.password)
                .then(function (response) {
                    console.log('Login response = ' + response.data);
                    if (response.data == 'true') {
                        $scope.alerte = "Connection succed";
                        $location.path("dashboard");
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
