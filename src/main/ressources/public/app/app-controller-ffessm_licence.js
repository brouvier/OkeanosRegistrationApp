/* 
 * Contrôleur de la liste des formations de plongée
 */
okeanosAppControllers.controller('ffessmLicenceCtrl', function ($scope, $http, securityService, FfessmLicence, Saison) {
    securityService.checkIsLogin();
    $scope.modeDebug = config.modeDebug;

    $scope.adminMode = securityService.checkIsAdmin();
    $scope.licenceList = FfessmLicence.query();
    $scope.saisonList = Saison.query();

    $http.get(config.okeanoAppUrl + 'saison/currentSaison')
        .then(function (response) {
            var saison = response.data;
            $scope.currentSaisonId = saison.id;
            console.log('currentSaisonId == ' + $scope.currentSaisonId);
        });

    $scope.saisonFilter = function (item) {
        if( $scope.currentSaisonId )
            return item.fk_saison_id == $scope.currentSaisonId;
        else return true;
    };

    $scope.getSaisonLabel = function (licence) {
        for (var i = 0; i < $scope.saisonList.length; i++) {
            if (($scope.saisonList[i].id) === licence.fk_saison_id) {
                return $scope.saisonList[i].label;
            }
        }
        return null;
    };

    $scope.edit = function (licence) {
        $scope.modalId = licence.id;
        $scope.modalSaisonId = licence.fk_saison_id;
        $scope.modalLabel = licence.label;
        $scope.modalPrice = licence.price;
    };

    $scope.saveItem = function () {
        console.log('controle sécu : ' + securityService.checkIsAdmin());
        if (securityService.checkIsAdmin() == false) {
            console.log('Enregistrement non authorisé');
            return false;
        }
        console.log('Enregistrement d une licence : ' + $scope.modalLabel);
        var licence = new FfessmLicence();
        licence.id = $scope.modalId;
        licence.fk_saison_id = $scope.modalSaisonId;
        licence.label = $scope.modalLabel;
        licence.price = $scope.modalPrice;
        console.log(licence);
        licence.$save(function (data, putResponseHeaders) {
            $scope.refreshList()
        });
        console.log('Enregistrement terminé');
    };

    $scope.remove = function (licence) {
        if (securityService.checkIsAdmin() == false) {
            console.log('Suppression non authorisé');
            return false;
        }
        console.log('Suppression de la licence : ' + licence.label);
        $scope.licenceList = [];
        FfessmLicence.delete(licence, function (data, responseHeaders) {
            $scope.refreshList()
        }, function (data, responseHeaders) {
            console.log("Erreur");
            console.log(responseHeaders);
            console.log(data);
        });
    };

    $scope.refreshList = function () {
        $scope.licenceList = [];
        $scope.modalId = null;
        $scope.modalSaisonId = null;
        $scope.modalLabel = null;
        $scope.modalPrice = null;
        $scope.licenceList = FfessmLicence.query();
    };
});
