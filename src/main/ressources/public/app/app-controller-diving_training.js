/* 
 * Contrôleur de la liste des formations de plongée
 */
okeanosAppControllers.controller('divingTrainingCtrl', function ($scope, securityService, DivingTraining) {
    securityService.checkIsLogin();
    $scope.modeDebug = modeDebug;

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
        training.$save();
        console.log('Enregistrement terminé');

        $scope.refreshList();
    };

    $scope.remove = function (training) {
        if (securityService.checkIsAdmin() == false) {
            console.log('Suppression non authorisé');
            return false;
        }
        console.log('Suppression du compte : ' + training.label);
        $scope.trainingList = [];
        DivingTraining.delete(training);

        $scope.refreshList();
    };

    $scope.refreshList = function () {
        $scope.trainingList = [];
        $scope.modalId = null;
        $scope.modalLabel = null;
        $scope.trainingList = DivingTraining.query();
    };
});
