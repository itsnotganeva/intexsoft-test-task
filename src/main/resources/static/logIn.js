var app = angular.module("Authentication", []);

var data;

app.controller("AuthController", function ($scope, $http, $location, $window) {

    $scope.auth = function() {
        var method = "";
        var url = "";

        method = "POST";
        url = '/auth';

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.registrationRequest),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(setToken);
    };
    function setToken(res) {
        this.data = res.data.token;
        $scope.redirectToAuth();
    }
    $scope.redirectToAuth = function () {
        var href = 'accounts.html';
        $window.location.href = href;
    }
});

app.controller("BankAccountController", function ($scope, $http) {

    $scope.bankAccounts=[];

    _refreshBankAccountsData();

    function _refreshBankAccountsData() {
        $http({
            method: 'GET',
            url: '/clients/2/bank-accounts',
            headers: {
                'Authorization': 'Bearer' + this.data
            }
        }).then(
            function(res) {
                $scope.bankAccounts = res.data;
            },
            function (res) {
                console.log("Error" + res.status + ":" + res.data);
            }
        );
    }

});

