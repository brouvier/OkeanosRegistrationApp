/* 
 * Contrôleur de la liste des formations de plongée
 */
okeanosAppControllers.controller('adherentInfoSaisonCtrl', function ($scope, $http, Upload, securityService, SubscriptionType, HockeyTeam, DivingTraining, AdherentInfoSaison, FfessmLicence, Subscription, Insurance) {
    securityService.checkIsLogin();
    $scope.modeDebug = modeDebug;

    var initAlerte = function (l, m) {
        $scope.processRunning = false;
        $scope.alerte = {
            level: l,
            message: m
        };
    };

    initAlerte('', '');

    $scope.subscriptionTypeList = SubscriptionType.query();
    $scope.hockeyTeamList = HockeyTeam.query();
    $scope.divingTrainingList = DivingTraining.query();

    var loadData = function () {
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
    };
    loadData();

    $scope.save = function () {
        initAlerte('', '');
        $scope.processRunning = true;
        console.log('Enregistrement : ');
        console.log($scope.adherentInfoSaison);
        $scope.adherentInfoSaison.$save(function () {
            initAlerte('alert-info', 'Mise à jour terminée avec succès.');
        }, function () {
            initAlerte('alerte-danger', 'Erreur dans la mise à jour des informations.');
        });
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
    $scope.docImportReady = function () {
        return $scope.adherentInfoSaison && $scope.adherentInfoSaison.id;
    };

    $scope.sickNoteUrl = '';
    $scope.parentalAgreementUrl = '';
    $scope.$watch("adherentInfoSaison.id", function (newValue, oldValue) {
        if ($scope.docImportReady()) {
            $scope.sickNoteUrl = okeanoAppUrl + "adherent_info_saison/" + $scope.adherentInfoSaison.id + "/sick_note";
            $scope.parentalAgreementUrl = okeanoAppUrl + "adherent_info_saison/" + $scope.adherentInfoSaison.id + "/parental_agreement";
        }
    });


    // upload on form submit
    $scope.sickNoteSubmit = function () {
        if ($scope.sickNoteForm.sickNoteFile.$valid && $scope.sickNoteFile) {
            $scope.upload($scope.sickNoteFile, $scope.sickNoteUrl);
            $scope.sickNoteFile = null;
        }
    };

    $scope.parentalAgreementSubmit = function () {
        if ($scope.parentalAgreementForm.parentalAgreementFile.$valid && $scope.parentalAgreementFile) {
            $scope.upload($scope.parentalAgreementFile, $scope.parentalAgreementUrl);
            $scope.parentalAgreementFile = null;
        }
    };

    // upload on file
    $scope.upload = function (file, url) {
        Upload.upload({
            url: url,
            data: {
                file: file
            }
        }).then(function (resp) {
            console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
        }, function (resp) {
            console.log('Error status: ' + resp.status);
        }, function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
        });

        loadData();
    };

});
