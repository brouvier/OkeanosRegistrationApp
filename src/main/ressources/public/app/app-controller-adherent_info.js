/* 
 * Contrôleur des informations adhérent
 */
okeanosAppControllers.controller('adherentInfoCtrl', function ($scope, $http, securityService, AdherentInfo) {
    securityService.checkIsLogin();
    $scope.modeDebug = modeDebug;

    AdherentInfo.get({
        id: securityService.getSecurity().curentAccountId
    }, function (ai, getResponseHeaders) {
        $scope.adherent = ai; // get row data
        $scope.adherent.birsthday = new Date($scope.adherent.birsthday); // convert filed to date
    });

    $scope.saveItem = function () {
        console.log('Enregistrement des informations adherent : ' + $scope.adherent.firstname);
        console.log('$scope.adherent.birsthday = ' + $scope.adherent.birsthday);
        console.log('$scope.adherent.birsthday is date = ' + angular.isDate($scope.adherent.birsthday));
        $scope.adherent.birsthday = new Date($scope.adherent.birsthday); // convert filed to date
        // $scope.adherent.birsthday = $filter('date')($scope.adherent.birsthday, "yyyy-MM-dd");  // convert filed to date
        $scope.adherent.$save();
        console.log('Enregistrement terminé');
    };
});
