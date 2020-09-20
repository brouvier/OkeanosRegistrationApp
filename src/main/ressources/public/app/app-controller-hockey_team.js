/* 
 * Contrôleur de la liste des formations de plongée
 */
okeanosAppControllers.controller('hockeyTeamCtrl', function ($scope, securityService, HockeyTeam) {
    securityService.checkIsLogin();
    $scope.modeDebug = config.modeDebug;

    $scope.adminMode = securityService.checkIsAdmin();
    $scope.teamList = HockeyTeam.query();

    $scope.edit = function (team) {
        $scope.modalId = team.id;
        $scope.modalLabel = team.label;
    };

    $scope.saveItem = function () {
        console.log('controle sécu : ' + securityService.checkIsAdmin());
        if (securityService.checkIsAdmin() == false) {
            console.log('Enregistrement non authorisé');
            return false;
        }
        console.log('Enregistrement d une equipe : ' + $scope.modalLabel);
        var team = new HockeyTeam();
        team.id = $scope.modalId;
        team.label = $scope.modalLabel;
        team.$save(function (data, putResponseHeaders) {
            $scope.refreshList()
        });
        console.log('Enregistrement terminé');
    };

    $scope.remove = function (team) {
        if (securityService.checkIsAdmin() == false) {
            console.log('Suppression non authorisé');
            return false;
        }
        console.log('Suppression du compte : ' + team.label);
        $scope.teamList = [];
        HockeyTeam.delete(team, function (data, responseHeaders) {
            $scope.refreshList()
        }, function (data, responseHeaders) {
            console.log("Erreur");
            console.log(responseHeaders);
            console.log(data);
        });
    };

    $scope.refreshList = function () {
        $scope.teamList = [];
        $scope.modalId = null;
        $scope.modalLabel = null;
        $scope.teamList = HockeyTeam.query();
    };
});
