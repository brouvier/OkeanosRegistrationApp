/* 
 * Contrôleur de la liste des comptes utilisateurs
 */
okeanosAppControllers.controller('accountListCtrl', function ($scope, securityService, Account) {
    securityService.checkIsLogin();

    $scope.accounts = Account.query();

    $scope.loadEdit = function (account) {
        $scope.newAccountId = account.id;
        $scope.newAccountMail = account.mail;
        $scope.newAccountAdmin = account.admin;
    };

    $scope.saveEdit = function () {
        var account = new Account();
        account.id = $scope.newAccountId;
        account.mail = $scope.newAccountMail;
        account.admin = $scope.newAccountAdmin;
        account.$save();

        $scope.refreshList();
    };

    $scope.remove = function (account) {
        console.log('Suppression du compte : ' + account.mail);
        $scope.accounts = [];
        Account.delete(account);

        $scope.refreshList();
    };

    $scope.refreshList = function () {
        $scope.subscriptionTypeList = [];
        $scope.newAccountId = null;
        $scope.newAccountMail = null;
        $scope.newAccountAdmin = null;
        $scope.accounts = Account.query();
    };
});
