var app = angular.module("Authentication", []);

app.controller("AuthController", function ($scope, $http, $location, $window) {

    $scope.auth = function() {
        $http({
            method: 'POST',
            url: '/auth',
            data: angular.toJson($scope.registrationRequest),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(setToken);
    }

    function setToken(res) {
        $window.localStorage.setItem('token', res.data.token);
        getClient();
    }

    function getClient() {
        $http({
            method: 'GET',
            url: '/current-client',
            headers: {
                'Authorization': 'Bearer ' + $window.localStorage.getItem('token')
            }
        }).then(setClient);
    }

    function setClient(res) {
        $window.localStorage.setItem('client', res.data.id);
        alert("Status: " + res.status);
        $scope.redirectToAccounts();
    }

    $scope.redirectToAccounts = function () {
        var href = 'accounts.html';
        $window.location.href = href;
    }

    $scope.redirectToRegister = function () {
        var href = 'register.html';
        $window.location.href = href;
    }
});

