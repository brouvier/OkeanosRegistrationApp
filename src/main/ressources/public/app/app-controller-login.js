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
            if ($scope.login.password == null || $scope.login.email == null || $scope.login.confirmPassword == null) {
                $scope.alerte = "Remplissez tous les champs";
            } else if ($scope.login.password != $scope.login.confirmPassword) {
                $scope.alerte = "Les deux mots de passe ne correspondent pas";
            } else {
                $http.get(okeanoAppUrl + 'security/signup/' + $scope.login.email + '/' + $scope.login.password
                + '/' + $scope.login.confirmPassword)
                    .then(function (response) {
                        if (response.data == "true") {
                            $location.path("dashboard");
                        } else {
                            $scope.alerte = "Erreur d'inscription";
                        }
                    });
            }
        } else {
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
