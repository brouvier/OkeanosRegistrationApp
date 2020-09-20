/* 
 * Contrôleur de la liste des formations de plongée
 */
okeanosAppControllers.controller('adherentInfoSaisonListCtrl', function ($scope, $http, $filter, Upload, securityService, filterService, Saison, AdherentInfo, AdherentInfoSaison, Subscription, HockeyTeam) {
    securityService.checkIsAdmin();
    $scope.modeDebug = config.modeDebug;

    $scope.hockeyTeamList = HockeyTeam.query();

    $http.get(config.okeanoAppUrl + '/saison')
        .then(function (response) {
            $scope.saisonList = response.data;
        });

    $scope.refreshList = function () {
        /* Recherche des informations des adhésion */
        $http.get(config.okeanoAppUrl + '/dashboard/saison/' + $scope.currentSaisonId)
            .then(function (response) {
                $scope.adherentList = response.data;
                for (var i = 0; i < $scope.adherentList.length; i++) {
                    var adherent = $scope.adherentList[i];
                    if (adherent.infoadherent && adherent.infoadherent.info && adherent.info.birsthday) {
                        adherent.info.birsthday = new Date(adherent.info.birsthday); // convert filed to date
                    }
                };
                console.log('Liste des adhérents mise à jour');
            });
        /* Recherche de la liste des types d'adésions */
        $scope.resetSubscriptionFilter();
        $http.get(config.okeanoAppUrl + '/subscription/saison/' + $scope.currentSaisonId)
            .then(function (response) {
                $scope.subscriptionList = response.data;
                console.log('Liste des adhésions mise à jour pour le filtre');
            });
    };

    $http.get(config.okeanoAppUrl + 'saison/currentSaison')
        .then(function (response) {
            var saison = response.data;
            $scope.currentSaisonId = saison.id;
            console.log('currentSaisonId == ' + $scope.currentSaisonId);
        });

    $scope.$watch("currentSaisonId", function (newValue, oldValue) {
        console.log('currentSaisonId == ' + $scope.currentSaisonId);
        if ($scope.currentSaisonId != null && newValue != oldValue) {
            console.log('currentSaisonId have change !');
            $scope.refreshList();
        }
    });

    /******************************
     * Gestion des fichiers joints
     *****************************/
    $scope.getSickNoteUrl = function (infoSaison) {
        if (infoSaison.id != null)
            return config.okeanoAppUrl + "adherent_info_saison/" + infoSaison.id + "/sick_note";
        else
            return null;
    };

    $scope.getParentalAgreementUrl = function (infoSaison) {
        if (infoSaison.id != null)
            return config.okeanoAppUrl + "adherent_info_saison/" + infoSaison.id + "/parental_agreement";
        else
            return null;
    };

    $scope.getCertificateLicenceUrl = function (infoSaison) {
        if (infoSaison && infoSaison.id != null)
            return config.okeanoAppUrl + "adherent_info_saison/" + infoSaison.id + "/certificate_licence";
        else
            return null;
    };

    /**************************************
     * Gestion de la pop up de validation
     *************************************/
    $scope.edit = function (accountId, infoSaisonId) {
        console.log('Edit : accountId = [' + accountId + '], infoSaisonId = [' + infoSaisonId + ']');
        if (accountId !== undefined) {
            AdherentInfo.get({
                id: accountId
            }, function (ai, getResponseHeaders) {
                $scope.modalInfo = ai; // get row data
                $scope.modalInfo.birsthday = new Date($scope.modalInfo.birsthday); // convert filed to date
            });
        } else {
            $scope.modalInfo = null;
        };
        if (infoSaisonId !== undefined) {
            AdherentInfoSaison.get({
                id: infoSaisonId
            }, function (ais, getResponseHeaders) {
                $scope.modalInfoSaison = ais; // get row data
                console.log($scope.modalInfoSaison);
            });
        } else {
            $scope.modalInfoSaison = null;
        };
    };

    $scope.saveItem = function () {
        console.log('controle sécu : ' + securityService.checkIsAdmin());
        if (securityService.checkIsAdmin() == false) {
            console.log('Enregistrement non authorisé');
            return false;
        }
        console.log('Enregistrement d une licence : ' + $scope.modalLabel);
        var temp = {};
        if ($scope.modalInfo !== null) {
            jQuery.extend(temp, $scope.modalInfo);
            temp.birsthday = $filter('date')(temp.birsthday, "yyyy-MM-dd"); // convert filed to string
            temp.$save();
        }
        if ($scope.modalInfoSaison !== null) {
            $scope.modalInfoSaison.$save();
        }
        console.log('Enregistrement terminé');

        $scope.refreshList();
    };

    /* Gestion des alerts sur la liste */
    $scope.adherentClass = function (adherent) {
        if (adherent.infoSaison.validation_payment_cashed == true) {
            return "";
        };
        if (adherent.infoSaison.validation_start == true) {
            return "warning";
        };
        return "danger";
    };

    /*****************
     * Import doc
     *****************/
    // upload on form submit
    $scope.certificateLicenceSubmit = function () {
        if ($scope.validationAdherentInfoSaisonForm.$valid && $scope.certificateLicenceFile) {
            $scope.upload($scope.certificateLicenceFile, $scope.getCertificateLicenceUrl($scope.modalInfoSaison));
            $scope.certificateLicenceFile = null;
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
            $scope.refreshList();
        }, function (resp) {
            console.log('Error status: ' + resp.status);
        }, function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
        });

        // Fermeture de la modal
        $('#editModal').modal('hide');
    };

    /*****************
     * Modal d'urgence
     *****************/

    $scope.emergency = function (adherent) {
        $scope.emergencyModalAdherent = adherent;
    }

    /*****************
     * Gestion des filtres
     *****************/

    $scope.modeVisu = filterService.modeVisu;
    $scope.modeFiltre = filterService.modeFiltre;
    $scope.subscriptionIdFilter = filterService.subscriptionIdFilter;

    $scope.resetSubscriptionFilter = function () {
        $scope.subscriptionIdFilter = 0;
    }

    $scope.adhesionFilter = function (adherent) {
        if ($scope.modeFiltre === 'notValid') {
            return !adherent.infoSaison.validation_payment_cashed;
        }
        if ($scope.modeFiltre === 'valid') {
            return adherent.infoSaison.validation_payment_cashed;
        }
        return true;
    };

    $scope.subscriptionFilter = function (adherent) {
        if ($scope.subscriptionIdFilter !== 0) {
            return $scope.subscriptionIdFilter === adherent.infoSaison.fk_subscription_id;
        }
        return true;
    };

    $scope.$watch("modeVisu", function (newValue, oldValue) {
        if ($scope.modeVisu != null && newValue != oldValue) {
            filterService.modeVisu = $scope.modeVisu;
        }
    });
    $scope.$watch("modeFiltre", function (newValue, oldValue) {
        if ($scope.modeFiltre != null && newValue != oldValue) {
            filterService.modeFiltre = $scope.modeFiltre;
        }
    });
    $scope.$watch("subscriptionIdFilter", function (newValue, oldValue) {
        if ($scope.subscriptionIdFilter != null && newValue != oldValue) {
            filterService.subscriptionIdFilter = $scope.subscriptionIdFilter;
        }
    });

});
