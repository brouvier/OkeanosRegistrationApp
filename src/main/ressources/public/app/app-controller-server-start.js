/* 
 * Contrôleur d'accès au serveur
 */
okeanosAppControllers.controller('serverStartCtrl', function ($scope, $location, securityService) {
    console.log('Init controler serverStartCtrl');
    $scope.modeDebug = config.modeDebug;

    // On contrôle que le serveur soit accessible
    if(config.serverUp){ 
        $scope.security = securityService.getSecurity();
        console.log('security', $scope.security);
        if($scope.security.isLogin == true){
            $location.path("dashboard");
        }
        else
            $location.path("userLogin");
    } else {
        console.log("Impossible de charger le fichier de prorpietés depuis le serveur !");
        $location.path("500");
    }
});