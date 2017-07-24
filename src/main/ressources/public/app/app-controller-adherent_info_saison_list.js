/* 
 * Contrôleur de la liste des formations de plongée
 */
okeanosAppControllers.controller('adherentInfoSaisonListCtrl', function ($scope, $http, securityService, Saison) {
    securityService.checkIsAdmin();

    $http.get(okeanoAppUrl + '/saison')
        .then(function (response) {
            $scope.saisonList = response.data;
        });

    var getList = function (saisonId) {
        /* Recherche des informations des adhésion */
        $http.get(okeanoAppUrl + '/adherent_info_saison/saison/' + saisonId)
            .then(function (response) {
                $scope.adherentList = response.data;
                for (var i = 0; i < $scope.adherentList.length; i++) {
                    var adherent = $scope.adherentList[i];
                    adherent.info.birsthday = new Date(adherent.info.birsthday); // convert filed to date
                }
            });
    };

    $http.get(okeanoAppUrl + 'saison/currentSaison')
        .then(function (response) {
            $scope.currentSaison = response.data;
            console.log('currentSaison == ' + $scope.currentSaison.label);
        });

    $scope.$watch("currentSaison", function (newValue, oldValue) {
        if ($scope.currentSaison != null) {
            console.log('currentSaison have change !')
            getList($scope.currentSaison.id);
        }
    });
});
