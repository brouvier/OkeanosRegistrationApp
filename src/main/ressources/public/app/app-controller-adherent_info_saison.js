/* 
 * Contrôleur de la liste des formations de plongée
 */
okeanosAppControllers.controller('adherentInfoSaisonCtrl', function ($scope, securityService, Subscription, Saison, SubscriptionType) {
    securityService.checkIsLogin();

    var saisonList = Saison.query();

    console.log('Search currentSaison');
    var sysdate = new Date();
    var currentSaison = null;

    console.log('sysdate');
    console.log(sysdate);
    console.log('saisonList');
    console.log(saisonList);
    console.log('saisonList.length = ' + saisonList.length);

    for (var i = 0; i < saisonList.length; i++) {
        console.log('start_date');
        console.log(saisonList[i].start_date);
        console.log('end_date');
        console.log(saisonList[i].end_date);
        if (saisonList[i].start_date >= sysdate && saisonList[i].end_date <= sysdate) {
            currentSaison = saisonList[i];
        }
    }
    if (currentSaison == null) {
        console.log('Error : currentSaison == null');
        return;
    }
    console.log('CurrentSaison == ' + currentSaison.label);
    /*
        $scope.subscriptionList = Subscription.query();
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

        $scope.saveItem = function () {
            console.log('Enregistrement : ' + $scope.modalLabel);
            console.log(subscription);
            subscription.$save();
            console.log('Enregistrement terminé');
        };
    */
});
