/* 
 * Contrôleur de la liste des formations de plongée
 */
okeanosAppControllers.controller('adherentInfoSaisonListCtrl', function ($scope, $http, securityService, Saison) {
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


});
