/* 
 * Contrôleur du dashboard
 */
okeanosAppControllers.controller('dashboardCtrl', function ($scope, $http, securityService) {
    securityService.checkIsLogin();
    $scope.modeDebug = modeDebug;
    $scope.okeanoAppUrl = okeanoAppUrl;

    $scope.adherentInfoPanel = "panel-red";
    $scope.adherentInfoStatus = "Inconnu !!!";

    $scope.membershipPanel = "panel-red";
    $scope.membershipStatus = "Inconnu !!!";

    $scope.documentPanel = "panel-red";
    $scope.documentStatus = "Inconnu !!!";

    $scope.totalCost = 0;

    var getTotalCost = function () {
        var cost = 0;
        if ($scope.adherent.insurance) {
            cost = $scope.adherent.insurance.price;
        }
        if ($scope.adherent.licence) {
            cost = cost + $scope.adherent.licence.price;
        }
        if ($scope.adherent.subscription) {
            cost = cost + $scope.adherent.subscription.price;
        }
        $scope.totalCost = cost;
    };

    $http.get(okeanoAppUrl + 'saison/currentSaison')
        .then(function (response) {
            var currentSaison = response.data;
            console.log('currentSaison == ' + currentSaison.label);
            var accountId = securityService.getSecurity().curentAccountId;

            if (currentSaison == null) {
                console.log('ERROR - currentSaison is empty');
                return;
            }
            if (accountId == null) {
                console.log('ERROR - curentAccountId is empty');
                return;
            }

            /* Recherche des informations de l'adherent */
            $http.get(okeanoAppUrl + 'dashboard/saison/' + currentSaison.id + '/account/' + accountId)
                .then(function (response) {
                    $scope.adherent = response.data;
                });
        });

    $scope.$watch("adherent", function (newValue, oldValue) {
        if ($scope.adherent != null) {

            if ($scope.adherent.info.id == null) {
                $scope.adherentInfoPanel = "panel-red";
                $scope.adherentInfoStatus = "A remplir";
            } else {
                $scope.adherentInfoPanel = "panel-primary";
                $scope.adherentInfoStatus = "Complètes";
            }

            if (!$scope.adherent.infoSaison || $scope.adherent.infoSaison.id == null) {
                $scope.membershipPanel = "panel-red";
                $scope.membershipStatus = "A remplir";
            } else {
                $scope.membershipPanel = "panel-primary";
                $scope.membershipStatus = "Complètes";
            }

            if (!$scope.adherent.infoSaison || $scope.adherent.infoSaison.fk_sick_note_id == null) {
                $scope.documentPanel = "panel-red";
                $scope.documentStatus = "A remplir";
            } else {
                $scope.documentPanel = "panel-primary";
                $scope.documentStatus = "Complètes";
            }

            getTotalCost();
        }
    });
});
