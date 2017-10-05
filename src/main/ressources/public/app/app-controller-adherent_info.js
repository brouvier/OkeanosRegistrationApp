/* 
 * Contrôleur des informations adhérent
 */
okeanosAppControllers.controller('adherentInfoCtrl', function ($scope, $http, $filter, securityService, AdherentInfo) {
    securityService.checkIsLogin();
    $scope.modeDebug = modeDebug;

    var initAlerte = function (l, m) {
        $scope.alerte = {
            level: l,
            message: m
        };
    };

    initAlerte('', '');

    AdherentInfo.get({
        id: securityService.getSecurity().curentAccountId
    }, function (ai, getResponseHeaders) {
        $scope.adherent = ai; // get row data
        console.log("$scope.adherent.birsthday=" + $scope.adherent.birsthday);
        $scope.adherent.birsthday = new Date($scope.adherent.birsthday); // convert filed to date
        console.log("$scope.adherent.birsthday=" + $scope.adherent.birsthday);
    });

    $scope.saveItem = function () {
        initAlerte('', '');
        console.log('Enregistrement des informations adherent : ' + $scope.adherent.firstname);
        var temp = {};
        jQuery.extend(temp, $scope.adherent);
        console.log("temp.birsthday=" + temp.birsthday);
        temp.birsthday = $filter('date')(temp.birsthday, "yyyy-MM-dd"); // convert filed to string
        console.log("temp.birsthday=" + temp.birsthday);
        temp.$save(function () {
            initAlerte('alert-info', 'Mise à jour terminée avec succès.');
        }, function () {
            initAlerte('alerte-danger', 'Erreur dans la mise à jour des informations.');
        });
        console.log('Enregistrement terminé');
    };
});
