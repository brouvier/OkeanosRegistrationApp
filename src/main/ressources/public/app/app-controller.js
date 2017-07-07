var okeanosAppControllers = angular.module('okeanosAppControllers', []);

/* 
 * Contrôleur d'ajout d'adhérents
 */
okeanosAppControllers.controller('adherentFormCtrl', function ($scope, $location, $http, securityService, sharedProperties) {
    securityService.checkIsLogin();

    $scope.updateState = '';
    $scope.adherent = sharedProperties.getCurrentEditedUser();

    if ($scope.adherent.birthday) {
        $scope.adherent.birthday = new Date($scope.adherent.birthday);
    };

    $scope.update = function (adherent) {
        $scope.updateState = 'wip';
        sharedProperties.setCurrentEditedUser(adherent);
        sharedProperties.UpdateUsersList(adherent);

        $http
            .post(okeanoAppUrl + 'adherent_info', adherent)
            .then(function (response) {
                console.log(response);
                if (response.status == 200) {
                    $scope.updateState = 'done';
                } else {
                    $scope.updateState = 'fail';
                }
            });
    };
});

/* 
 * Contrôleur de la liste des utilisateurs
 */
okeanosAppControllers.controller('listUsrCtrl', function ($scope, $location, securityService, sharedProperties) {
    securityService.checkIsAdmin();

    // initialisation
    $scope.users = sharedProperties.getUsersList();

    $scope.gotoEditForm = function () {
        sharedProperties.setCurrentEditedUser(this.user);
        $location.path("userForm");
    };
});
