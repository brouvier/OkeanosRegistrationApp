/* 
 * Contrôleur de la liste des assurances
 */
okeanosAppControllers.controller('insuranceCtrl', function ($scope, securityService, Insurance, Saison) {
    securityService.checkIsLogin();

    $scope.adminMode = securityService.checkIsAdmin();
    $scope.insuranceList = Insurance.query();
    $scope.saisonList = Saison.query();

    $scope.getSaisonLabel = function (insurance) {
        for (var i = 0; i < $scope.saisonList.length; i++) {
            if (($scope.saisonList[i].id) === insurance.fk_saison_id) {
                return $scope.saisonList[i].label;
            }
        }
        return null;
    };

    $scope.edit = function (insurance) {
        $scope.modalId = insurance.id;
        $scope.modalSaisonId = insurance.fk_saison_id;
        $scope.modalLabel = insurance.label;
        $scope.modalPrice = insurance.price;
    };

    $scope.saveItem = function () {
        console.log('controle sécu : ' + securityService.checkIsAdmin());
        if (securityService.checkIsAdmin() == false) {
            console.log('Enregistrement non authorisé');
            return false;
        }
        console.log('Enregistrement d une assurance : ' + $scope.modalLabel);
        var insurance = new Insurance();
        insurance.id = $scope.modalId;
        insurance.fk_saison_id = $scope.modalSaisonId;
        insurance.label = $scope.modalLabel;
        insurance.price = $scope.modalPrice;
        console.log(insurance);
        insurance.$save();
        console.log('Enregistrement terminé');

        $scope.refreshList();
    };

    $scope.remove = function (insurance) {
        if (securityService.checkIsAdmin() == false) {
            console.log('Suppression non authorisé');
            return false;
        }
        console.log('Suppression de la insurance : ' + insurance.label);
        $scope.insuranceList = [];
        Insurance.delete(insurance);

        $scope.refreshList();
    };

    $scope.refreshList = function () {
        $scope.insuranceList = [];
        $scope.modalId = null;
        $scope.modalSaisonId = null;
        $scope.modalLabel = null;
        $scope.modalPrice = null;
        $scope.insuranceList = Insurance.query();
    };
});
