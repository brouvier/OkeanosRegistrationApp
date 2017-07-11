/* 
 * Contrôleur du dashboard
 */
okeanosAppControllers.controller('dashboardCtrl', function ($scope, $http, securityService, AdherentInfo) {
    securityService.checkIsLogin();
    /*
        var adherent = AdherentInfo.get({
            id: securityService.getSecurity().curentAccountId
        });

        if (adherent.licence_number == "") {
            $scope.adherentInfoPanel = "panel-red";
            $scope.adherentInfoStatus = "Incomplètes";
        } else {
            $scope.adherentInfoPanel = "panel-primary";
            $scope.adherentInfoStatus = "Complètes";

        }*/


    $scope.adherentInfoPanel = "panel-primary";
    $scope.adherentInfoStatus = "Complètes";
});
