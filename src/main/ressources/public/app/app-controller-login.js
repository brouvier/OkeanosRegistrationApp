/* 
 * Contrôleur des données de connexion
 */
okeanosAppControllers.controller('loginCtrl', function ($scope, $location, $http, securityService) {
    console.log('Init controler loginCtrl');
    $scope.modeDebug = modeDebug;
    $scope.security = securityService.getSecurity();
    $scope.alerte = "none";
    $scope.login = {};

    $scope.login = function () {
        $scope.alerte = "none";
        console.log("$scope.login.mode : " + $scope.login.mode);

        var indata = {
            'mail': $scope.login.email,
            'password': String(CryptoJS.SHA256($scope.login.email + $scope.login.password))
        };

        console.log(indata);

        if ($scope.login.mode == "Inscription") {
            // Sign up
            if ($scope.login.password == null || $scope.login.email == null || $scope.login.confirmPassword == null) {
                $scope.alerte = "Remplissez tous les champs";
            } else if ($scope.login.password != $scope.login.confirmPassword) {
                $scope.alerte = "Les deux mots de passe ne correspondent pas";
            } else {
                $http.post(okeanoAppUrl + 'security/signup', indata).then(function (response) {
                    if (response.data == "true") {
                        $location.path("dashboard");
                    } else {
                        $scope.alerte = "Erreur d'inscription : " + response.data;
                    }
                }, function (response) {
                    $scope.alerte = "Erreur d'inscription !";
                });
            }
        } else {
            // Login
            if ($scope.login.password == null || $scope.login.email == null) {
                $scope.alerte = "Remplissez tous les champs";
            } else {
                $http.post(okeanoAppUrl + 'security/login', indata).then(function (response) {
                    console.log('Login response');
                    console.log(response);
                    if (response.data == 'true') {
                        $location.path("dashboard");
                    } else {
                        $scope.alerte = "Erreur de connexion : " + response.data;
                    }
                }, function (response) {
                    $scope.alerte = "Erreur de connexion !";
                });
            }
        }

    };

    $scope.logout = function () {
        $http.get(okeanoAppUrl + 'security/logout')
            .then(function (response) {
                console.log(response);
                if (response.data == 'true') {
                    $location.path("userLogin");
                } else {
                    $scope.alerte = "Erreur de connexion : " + data;
                }
            });
    };
});
