/* 
 * Contrôleur de la liste des formations de plongée
 */
okeanosAppControllers.controller('adherentInfoSaisonCtrl', function ($scope, $http, securityService, Subscription, SubscriptionType, FfessmLicence, AdherentInfoSaison, HockeyTeam, Insurance, DivingTraining) {
    securityService.checkIsLogin();
    $scope.modeDebug = modeDebug;

    $scope.subscriptionTypeList = SubscriptionType.query();
    $scope.hockeyTeamList = HockeyTeam.query();
    $scope.divingTrainingList = DivingTraining.query();

    $http.get(okeanoAppUrl + 'saison/currentSaison')
        .then(function (response) {
            $scope.currentSaison = response.data;
            console.log('currentSaison == ' + $scope.currentSaison.label);


            /* Listes filtrees par saison */
            $http.get(okeanoAppUrl + 'ffessm_licence/saison/' + $scope.currentSaison.id)
                .then(function (response) {
                    $scope.licenceList = response.data;
                });
            $http.get(okeanoAppUrl + 'subscription/saison/' + $scope.currentSaison.id)
                .then(function (response) {
                    $scope.subscriptionList = response.data;
                });
            $http.get(okeanoAppUrl + 'insurance/saison/' + $scope.currentSaison.id)
                .then(function (response) {
                    $scope.insuranceList = response.data;
                });

            /* Recherche des informations d'adhésion */
            $http.get(okeanoAppUrl + 'adherent_info_saison/saison/' + $scope.currentSaison.id + '/account/' + securityService.getSecurity().curentAccountId)
                .then(function (response) {
                    var adherent_info_saison_id = response.data;
                    console.log('adherent_info_saison_id == ' + response.data);

                    if (adherent_info_saison_id == "null") {
                        $scope.adherentInfoSaison = new AdherentInfoSaison();
                        $scope.adherentInfoSaison.fk_saison_id = $scope.currentSaison.id;
                        $scope.adherentInfoSaison.fk_account_id = securityService.getSecurity().curentAccountId;
                    } else {
                        $scope.adherentInfoSaison = AdherentInfoSaison.get({
                            id: adherent_info_saison_id
                        });
                    };

                    console.log('adherentInfoSaison');
                    console.log($scope.adherentInfoSaison);

                });
        });

    $scope.save = function () {
        console.log('Enregistrement : ');
        console.log($scope.adherentInfoSaison);
        $scope.adherentInfoSaison.$save();
        console.log('Enregistrement terminé');
    };
});
