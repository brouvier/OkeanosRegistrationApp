/* 
 * Contrôleur de la liste des formations de plongée
 */
okeanosAppControllers.controller('subscriptionCtrl', function ($scope, securityService, Subscription, Saison, SubscriptionType) {
    securityService.checkIsLogin();
    $scope.modeDebug = modeDebug;

    $scope.adminMode = securityService.checkIsAdmin();
    $scope.subscriptionList = Subscription.query();
    $scope.saisonList = Saison.query();
    $scope.subscriptionTypeList = SubscriptionType.query();

    $scope.getSaisonLabel = function (subscription) {
        for (var i = 0; i < $scope.saisonList.length; i++) {
            if (($scope.saisonList[i].id) === subscription.fk_saison_id) {
                return $scope.saisonList[i].label;
            }
        }
        return null;
    };
    $scope.getSubscriptionTypeLabel = function (subscription) {
        for (var i = 0; i < $scope.subscriptionTypeList.length; i++) {
            if (($scope.subscriptionTypeList[i].id) === subscription.fk_subscription_type_id) {
                return $scope.subscriptionTypeList[i].label;
            }
        }
        return null;
    };

    $scope.edit = function (subscription) {
        $scope.modalId = subscription.id;
        $scope.modalSaisonId = subscription.fk_saison_id;
        $scope.modalSubscriptionTypeId = subscription.fk_subscription_type_id;
        $scope.modalLabel = subscription.label;
        $scope.modalPrice = subscription.price;
    };

    $scope.saveItem = function () {
        console.log('controle sécu : ' + securityService.checkIsAdmin());
        if (securityService.checkIsAdmin() == false) {
            console.log('Enregistrement non authorisé');
            return false;
        }
        console.log('Enregistrement d un tarif d adhesion : ' + $scope.modalLabel);
        var subscription = new Subscription();
        subscription.id = $scope.modalId;
        subscription.fk_saison_id = $scope.modalSaisonId;
        subscription.fk_subscription_type_id = $scope.modalSubscriptionTypeId;
        subscription.label = $scope.modalLabel;
        subscription.price = $scope.modalPrice;
        console.log(subscription);
        subscription.$save(function (data, putResponseHeaders) {
            $scope.refreshList()
        });
        console.log('Enregistrement terminé');
    };

    $scope.remove = function (subscription) {
        if (securityService.checkIsAdmin() == false) {
            console.log('Suppression non authorisé');
            return false;
        }
        console.log('Suppression du tarif d adhesion : ' + subscription.label);
        $scope.subscriptionList = [];
        Subscription.delete(subscription, function (data, responseHeaders) {
            $scope.refreshList()
        }, function (data, responseHeaders) {
            console.log("Erreur");
            console.log(responseHeaders);
            console.log(data);
        });
    };

    $scope.refreshList = function () {
        $scope.subscriptionList = [];
        $scope.modalId = null;
        $scope.modalSaisonId = null;
        $scope.modalSubscriptionTypeId = null;
        $scope.modalLabel = null;
        $scope.modalPrice = null;
        $scope.subscriptionList = Subscription.query();
    };
});
