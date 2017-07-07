/* 
 * Contrôleur de la liste des saisons
 */
okeanosAppControllers.controller('adherentInfoCtrl', function ($scope, securityService, AdherentInfo) {
    securityService.checkIsLogin();

    $scope.adminMode = securityService.checkIsAdmin();
    $scope.adherentInfo = AdherentInfo.get({
        id: securityService.getSecurity().curentAccountId
    });

    $scope.saveItem = function () {
        console.log('Enregistrement des informations adherent : ' + $scope.adherentInfo);
        adherentInfo.$save();
        console.log('Enregistrement terminé');

        $scope.refreshList();
    };
});
