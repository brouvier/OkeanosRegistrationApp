/* 
 * Contrôleur de mise à jour du mot de passe
 */
okeanosAppControllers.controller('passwordCtrl', function ($scope, $routeParams, $location, $http, securityService) {
    console.log('Init controler passwordCtrl');
    $scope.modeDebug = config.modeDebug;
    $scope.security = securityService.getSecurity();

    var initAlerte = function () {
        $scope.alerte = {
            level: '',
            message: ''
        };
    };

    initAlerte();

    $scope.ticket = $routeParams.ticket;

    $scope.requestNewPass = function () {
        initAlerte();

        $http.get(config.okeanoAppUrl + 'security/requestNewPass/' + $scope.email).then(function (response) {
            $scope.alerte = response.data;
        }, function (response) {
            $scope.alerte = {
                level: 'alert-danger',
                message: 'Une erreur est survenue !'
            };
        });

    };

    $scope.updatePass = function () {
        initAlerte();

        var indata = {
            'mail': $scope.login.email,
            'password': String(CryptoJS.SHA256($scope.login.email + $scope.login.password))
        };

        // Contrôle des informations de login
        if ($scope.login.password == null || $scope.login.email == null || $scope.login.confirmPassword == null) {
            $scope.alerte = {
                level: 'alert-danger',
                message: 'Merci de remplir tous les champs'
            };
        } else // Contrôle de corespondance des mots de passe
            if ($scope.login.password != $scope.login.confirmPassword) {
                $scope.alerte = {
                    level: 'alert-danger',
                    message: 'Les deux mots de passe ne correspondent pas !'
                };
            } else // Mise à jour du mot de passe
        {
            $http.post(config.okeanoAppUrl + 'security/updatePass/' + $scope.ticket, indata).then(function (response) {
                $scope.alerte = response.data;
            }, function (response) {
                $scope.alerte = {
                    level: 'alert-danger',
                    message: 'Une erreur est survenue !'
                };
            });
        }

    };


});
