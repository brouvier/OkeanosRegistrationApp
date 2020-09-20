/* 
 * Contrôleur de la liste des formations de plongée
 */
okeanosAppControllers.controller('divingTrainingCtrl', function ($scope, securityService, DivingTraining) {
    securityService.checkIsLogin();
    $scope.modeDebug = config.modeDebug;

    $scope.adminMode = securityService.checkIsAdmin();
    $scope.trainingList = DivingTraining.query();

    $scope.edit = function (training) {
        $scope.modalId = training.id;
        $scope.modalLabel = training.label;
    };

    $scope.saveItem = function () {
        console.log('controle sécu : ' + securityService.checkIsAdmin());
        if (securityService.checkIsAdmin() == false) {
            console.log('Enregistrement non authorisé');
            return false;
        }
        console.log('Enregistrement d un training : ' + $scope.modalLabel);
        var training = new DivingTraining();
        training.id = $scope.modalId;
        training.label = $scope.modalLabel;
        training.$save(function (data, putResponseHeaders) {
            $scope.refreshList()
        });
        console.log('Enregistrement terminé');
    };

    $scope.remove = function (training) {
        if (securityService.checkIsAdmin() == false) {
            console.log('Suppression non authorisé');
            return false;
        }
        console.log('Suppression du compte : ' + training.label);
        $scope.trainingList = [];
        DivingTraining.delete(training,
            function (data, responseHeaders) {
                $scope.refreshList()
            },
            function (data, responseHeaders) {
                console.log("Erreur");
                console.log(responseHeaders);
                console.log(data);
            });
    };

    $scope.refreshList = function () {
        $scope.trainingList = [];
        $scope.modalId = null;
        $scope.modalLabel = null;
        $scope.trainingList = DivingTraining.query();
    };
});
