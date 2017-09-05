var okeanosAppControllers = angular.module('okeanosAppControllers', []);

/* 
 * Contrôleur de test pour la gestion des fichiers
 */
okeanosAppControllers.controller('sickNoteUploadCtrl', function ($scope, $http, securityService, Upload) {
    // securityService.checkIsAdmin();
    $scope.modeDebug = modeDebug;
    $scope.document = {};

    $scope.$watch("document.file", function (newValue, oldValue) {
        console.log('document change');
        $scope.document.error = '';
        if ($scope.document.file && $scope.document.file.size > 1024 * 1024) { /* 1Mo */
            $scope.document.error = 'Taille du fichier > 1Mo, merci de le compresser.';
            console.log('File to big !!!');
        }
    });

    // upload later on form submit or something similar
    $scope.ngFileUploadSubmit = function () {
        if ($scope.form.file.$valid && $scope.file) {
            $scope.upload($scope.file);
        }
    };

    // upload on file select or drop
    $scope.upload = function (file) {
        Upload.upload({
            url: 'https://localhost:8080/api/v1/file/multipart',
            data: {
                file: file
            }
        }).then(function (resp) {
            console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
        }, function (resp) {
            console.log('Error status: ' + resp.status);
        }, function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
        });
    };

});
