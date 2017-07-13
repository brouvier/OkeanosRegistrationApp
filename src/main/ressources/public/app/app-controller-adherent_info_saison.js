/* 
 * Contrôleur de la liste des formations de plongée
 */
okeanosAppControllers.controller('adherentInfoSaisonCtrl', function ($scope, $http, securityService, Subscription, Saison, SubscriptionType, FfessmLicence, AdherentInfoSaison, HockeyTeam, DivingTraining) {
    securityService.checkIsLogin();

    /* TODO remplacer par un unique appel */
    var saisonList = Saison.query(function () {
        console.log('Search currentSaison');
        var sysdate = new Date().getTime();
        $scope.currentSaison = null;

        for (var i = 0; i < saisonList.length; i++) {
            var start = new Date(saisonList[i].start_date).getTime();
            var end = new Date(saisonList[i].end_date).getTime();
            if (start <= sysdate && end > sysdate) {
                $scope.currentSaison = saisonList[i];
            }
        }
        if ($scope.currentSaison == null) {
            console.log('Error : currentSaison == null');
            return;
        }
        console.log('CurrentSaison == ' + $scope.currentSaison.label);

        /* TODO Filtrer par saison */
        $scope.licenceList = FfessmLicence.query();
        $scope.subscriptionList = Subscription.query();

        /* TODO créer la ressource 
        $http.get(okeanoAppUrl + 'adherent_info_saison/account/' + securityService.getSecurity.curentAccountId + '/saison/' + $scope.currentSaison.id)
            .then(function (response) {
                console.log('Sign up response = ' + response.data);
                $scope.adherentInfoSaison = angular.fromJson(response.data);
            });*/
    });

    $scope.subscriptionTypeList = SubscriptionType.query();
    $scope.hockeyTeamList = HockeyTeam.query();
    $scope.divingTrainingList = DivingTraining.query();

    $scope.save = function () {
        console.log('Enregistrement : ');
        var item = new AdherentInfoSaison();
        item.id = null;
        item.fk_account_id = null;
        item.fk_saison_id = $scope.adherentInfoSaison.fk_saison_id;
        item.fk_ffessm_licence_id = $scope.adherentInfoSaison.licence;
        item.fk_subscription_id = $scope.adherentInfoSaison.subscription;
        item.fk_insurance_id = null;
        item.picture_authorisation = $scope.adherentInfoSaison.picture_authorisation;
        item.fk_actual_training_id = $scope.adherentInfoSaison.actualLevel;
        item.fk_training_id = $scope.adherentInfoSaison.preparedLevel;
        item.fk_team_id = $scope.adherentInfoSaison.team;
        console.log(item);
        //item.$save();
        console.log('Enregistrement terminé');
    };
});
