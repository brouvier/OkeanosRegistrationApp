/* 
 * Contrôleur de la liste des saisons
 */
okeanosAppControllers.controller('adherentInfoCtrl', function ($scope, $http, securityService, AdherentInfo) {
    securityService.checkIsLogin();

    $scope.adherent = AdherentInfo.get({
        id: securityService.getSecurity().curentAccountId
    });

    $scope.saveItem = function () {
        console.log('Enregistrement des informations adherent : ' + $scope.adherent);
        $scope.adherent.$save();
        console.log('Enregistrement terminé');
    };
});
