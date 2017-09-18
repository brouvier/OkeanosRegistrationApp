/* 
 * Contrôleur de la liste des formations de plongée
 */
okeanosAppControllers.controller('adherentInfoSaisonListCtrl', function ($scope, $http, $filter, securityService, Saison, AdherentInfo, AdherentInfoSaison) {
    securityService.checkIsAdmin();
    $scope.modeDebug = modeDebug;

    $http.get(okeanoAppUrl + '/saison')
        .then(function (response) {
            $scope.saisonList = response.data;
        });

    var getList = function (saisonId) {
        /* Recherche des informations des adhésion */
        $http.get(okeanoAppUrl + '/dashboard/saison/' + saisonId)
            .then(function (response) {
                $scope.adherentList = response.data;
                for (var i = 0; i < $scope.adherentList.length; i++) {
                    var adherent = $scope.adherentList[i];
                    adherent.info.birsthday = new Date(adherent.info.birsthday); // convert filed to date
                }
            });
    };

    $http.get(okeanoAppUrl + 'saison/currentSaison')
        .then(function (response) {
            $scope.currentSaison = response.data;
            console.log('currentSaison == ' + $scope.currentSaison.label);
        });

    $scope.$watch("currentSaison", function (newValue, oldValue) {
        if ($scope.currentSaison != null) {
            console.log('currentSaison have change !')
            getList($scope.currentSaison.id);
        }
    });

    /* Gestion des fichiers joints */
    $scope.getSickNoteUrl = function (infoSaison) {
        if (infoSaison.id != null)
            return okeanoAppUrl + "adherent_info_saison/" + infoSaison.id + "/sick_note";
        else
            return null;
    };

    $scope.getParentalAgreementUrl = function (infoSaison) {
        if (infoSaison.id != null)
            return okeanoAppUrl + "adherent_info_saison/" + infoSaison.id + "/parental_agreement";
        else
            return null;
    };


    /* Gestion de la pop up de validation */
    $scope.edit = function (accountId, infoSaisonId) {
        AdherentInfo.get({
            id: accountId
        }, function (ai, getResponseHeaders) {
            $scope.modalInfo = ai; // get row data
            $scope.modalInfo.birsthday = new Date($scope.modalInfo.birsthday); // convert filed to date
        });
        AdherentInfoSaison.get({
            id: infoSaisonId
        }, function (ai, getResponseHeaders) {
            $scope.modalInfoSaison = ai; // get row data
            console.log($scope.modalInfoSaison);
        });
    };

    $scope.saveItem = function () {
        console.log('controle sécu : ' + securityService.checkIsAdmin());
        if (securityService.checkIsAdmin() == false) {
            console.log('Enregistrement non authorisé');
            return false;
        }
        console.log('Enregistrement d une licence : ' + $scope.modalLabel);
        var temp = {};
        jQuery.extend(temp, $scope.modalInfo);
        temp.birsthday = $filter('date')(temp.birsthday, "yyyy-MM-dd"); // convert filed to string
        temp.$save();
        $scope.modalInfoSaison.$save();
        console.log('Enregistrement terminé');

        getList($scope.currentSaison.id);
    };

    /* Gestion des alters sur la liste */
    $scope.adherentClass = function (adherent) {
        if (adherent.infoSaison.validation_payment_cashed == true) {
            return "";
        };
        if (adherent.infoSaison.validation_start == true) {
            return "warning";
        };
        return "danger";
    };
});
