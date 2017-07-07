/* 
 * Contrôleur de la liste des types d'adhésions
 */
okeanosAppControllers.controller('subscriptionTypeCtrl', function ($scope, $location, $http, securityService, SubscriptionType) {
    securityService.checkIsAdmin();

    console.log('Appel de la liste des type de souscription');
    $scope.refreshSubscriptionType();

    $scope.createSubscriptionType = function () {
        console.log('Création d un element');
        var sType = new SubscriptionType();
        sType.id = $scope.newSTypeId;
        sType.label = $scope.newSTypeLabel;
        sType.$save();

        $scope.refreshSubscriptionType();
    };

    $scope.editSubscriptionType = function (sType) {
        console.log('Edition d un element');
        $scope.newSTypeId = sType.id;
        $scope.newSTypeLabel = sType.label;
    };

    $scope.removeSubscriptionType = function (sType) {
        $scope.subscriptionTypeList = [];
        SubscriptionType.delete(sType);

        $scope.refreshSubscriptionType();
    };

    $scope.refreshSubscriptionType = function () {
        $scope.newSTypeId = null;
        $scope.newSTypeLabel = null;
        $scope.subscriptionTypeList = SubscriptionType.query();
    };
});
