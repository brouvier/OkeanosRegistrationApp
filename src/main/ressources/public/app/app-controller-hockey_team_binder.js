/* 
 * Contrôleur de la liste des formations de plongée
 */
okeanosAppControllers.controller('hockeyTeamBinderCtrl', function ($scope, $http, securityService, HockeyTeam, Saison) {
    securityService.checkIsLogin();
    $scope.modeDebug = config.modeDebug;

    $scope.adminMode = securityService.checkIsAdmin();
    $scope.teamList = HockeyTeam.query();
    $scope.saisonList = Saison.query();

    $http.get(config.okeanoAppUrl + 'saison/currentSaison')
        .then(function (response) {
            var saison = response.data;
            $scope.currentSaisonId = saison.id;
            console.log('currentSaisonId == ' + $scope.currentSaisonId);
        });



    $scope.getBinderUrl = function (team) {
        if (team.id != null)
            return config.okeanoAppUrl + "hockey_team/binder_team/" + team.id + "/saison/" + $scope.currentSaisonId;
        else
            return null;
    };
});
