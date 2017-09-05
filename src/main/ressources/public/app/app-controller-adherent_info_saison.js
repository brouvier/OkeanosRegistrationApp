/* 
 * Contrôleur de la liste des formations de plongée
 */
okeanosAppControllers.controller('adherentInfoSaisonCtrl', function ($scope, $http, securityService, SubscriptionType, HockeyTeam, DivingTraining, AdherentInfoSaison, FfessmLicence, Subscription, Insurance) {
    securityService.checkIsLogin();
    $scope.modeDebug = modeDebug;

    $scope.subscriptionTypeList = SubscriptionType.query();
    $scope.hockeyTeamList = HockeyTeam.query();
    $scope.divingTrainingList = DivingTraining.query();

    $http.get(okeanoAppUrl + 'saison/currentSaison')
        .then(function (response) {
            $scope.currentSaison = response.data;
            console.log('currentSaison == ' + $scope.currentSaison.label);

            if ($scope.currentSaison == null) {
                console.log('ERROR - currentSaison is empty');
                return;
            }

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

    /*****************
     * Total price
     *****************/
    var licencePrice = 0;
    var subscriptionPrice = 0;
    var insurancePrice = 0;

    var getTotalPrice = function () {
        $scope.totalPrice = licencePrice + subscriptionPrice + insurancePrice;
    };

    $scope.$watch("adherentInfoSaison.fk_ffessm_licence_id", function (newValue, oldValue) {
        if ($scope.adherentInfoSaison) {
            if ($scope.adherentInfoSaison.fk_ffessm_licence_id) {
                var licence = FfessmLicence.get({
                    id: $scope.adherentInfoSaison.fk_ffessm_licence_id
                }, function () {
                    licencePrice = licence.price;
                    getTotalPrice();
                });

            };
        };
    });

    $scope.$watch("adherentInfoSaison.fk_subscription_id", function (newValue, oldValue) {
        if ($scope.adherentInfoSaison) {
            if ($scope.adherentInfoSaison.fk_subscription_id) {
                var subscription = Subscription.get({
                    id: $scope.adherentInfoSaison.fk_subscription_id
                }, function () {
                    subscriptionPrice = subscription.price;
                    getTotalPrice();
                });

            };
        };
    });

    $scope.$watch("adherentInfoSaison.fk_insurance_id", function (newValue, oldValue) {
        if ($scope.adherentInfoSaison) {
            if ($scope.adherentInfoSaison.fk_insurance_id) {
                var insurance = Insurance.get({
                    id: $scope.adherentInfoSaison.fk_insurance_id
                }, function () {
                    insurancePrice = insurance.price;
                    getTotalPrice();
                });

            };
        };
    });

    /*****************
     * Import doc
     *****************/
    $scope.sickNote = {};
    $scope.parentalAgreement = {};
    $scope.sickNote.url = '';
    $scope.parentalAgreement.url = '';
    $scope.$watch("adherentInfoSaison.id", function (newValue, oldValue) {
        if ($scope.docImportReady()) {
            $scope.sickNote.url = okeanoAppUrl + "adherent_info_saison/" + $scope.adherentInfoSaison.id + "/sick_note";
            $scope.parentalAgreement.url = okeanoAppUrl + "adherent_info_saison/" + $scope.adherentInfoSaison.id + "/parental_agreement";
        }
    });

    $scope.docImportReady = function () {
        return $scope.adherentInfoSaison && $scope.adherentInfoSaison.id;
    };

    /*****************
     * Sick Note
     *****************/
    $scope.$watch("sickNote.file", function (newValue, oldValue) {
        console.log('sickNote change');
        $scope.sickNote.error = '';
        if ($scope.sickNote.file && $scope.sickNote.file.size > 1024 * 1024) { /* 1Mo */
            $scope.sickNote.error = 'Taille du fichier > 1Mo, merci de le compresser.';
        }
    });

    $scope.sickNoteExportReady = function () {
        return $scope.adherentInfoSaison && $scope.adherentInfoSaison.fk_sick_note_id;
    };

    /*****************
     * parentalAgreement
     *****************/
    $scope.$watch("parentalAgreement.file", function (newValue, oldValue) {
        console.log('parentalAgreement change');
        $scope.parentalAgreement.error = '';
        if ($scope.parentalAgreement.file && $scope.parentalAgreement.file.size > 1024 * 1024) { /* 1Mo */
            $scope.parentalAgreement.error = 'Taille du fichier > 1Mo, merci de le compresser.';
        }
    });

    $scope.parentalAgreementExportReady = function () {
        return $scope.adherentInfoSaison && $scope.adherentInfoSaison.fk_parental_agreement_id;
    };



});
