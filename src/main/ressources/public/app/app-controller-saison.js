/* 
 * Contrôleur de la liste des saisons
 */
okeanosAppControllers.controller('saisonListCtrl', function ($scope, $filter, securityService, Saison) {
    securityService.checkIsLogin();
    $scope.modeDebug = modeDebug;

    $scope.adminMode = securityService.checkIsAdmin();

    $scope.edit = function (saison) {
        $scope.modalId = saison.id;
        $scope.modalLabel = saison.label;
        $scope.modalStartDate = new Date(saison.start_date);
        $scope.modalEndDate = new Date(saison.end_date);
    };

    $scope.saveItem = function () {
        console.log('controle sécu : ' + securityService.checkIsAdmin());
        if (securityService.checkIsAdmin() == false) {
            console.log('Enregistrement non authorisé');
            return false;
        }
        console.log('Enregistrement d une saison : ' + $scope.modalLabel);
        var saison = new Saison();
        saison.id = $scope.modalId;
        saison.label = $scope.modalLabel;
        saison.start_date = $filter('date')($scope.modalStartDate, "yyyy-MM-dd"); // convert filed to string
        saison.end_date = $filter('date')($scope.modalEndDate, "yyyy-MM-dd"); // convert filed to string
        saison.$save(function (data, putResponseHeaders) {
            // Refresh after save
            $scope.refreshList()
        });
        console.log('Enregistrement terminé');
    };

    $scope.remove = function (saison) {
        if (securityService.checkIsAdmin() == false) {
            console.log('Suppression non authorisé');
            return false;
        }
        console.log('Suppression de la saison : ' + saison.label);
        $scope.saisonList = [];
        Saison.delete(saison, function (data, responseHeaders) {
            $scope.refreshList()
        }, function (data, responseHeaders) {
            console.log("Erreur");
            console.log(responseHeaders);
            console.log(data);
        });
    };

    $scope.refreshList = function () {
        $scope.saisonList = [];
        $scope.modalId = null;
        $scope.modalLabel = null;
        $scope.modalStartDate = null;
        $scope.modalEndDate = null;
        //$scope.saisonList = Saison.query();

        Saison.query(function (sl, getResponseHeaders) {
            $scope.saisonList = sl; // get row data
            for (var i = 0; i < $scope.saisonList.length; i++) {
                var saison = $scope.saisonList[i];
                saison.start_date = new Date(saison.start_date); // convert filed to date
                saison.end_date = new Date(saison.end_date); // convert filed to date
            }
        });
    };

    $scope.refreshList();
});
