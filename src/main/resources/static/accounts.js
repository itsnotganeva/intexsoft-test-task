var app = angular.module("Accounts", []);

app.controller("BankAccountController", function ($scope, $http, $window) {

    $scope.bankAccounts=[];

    _refreshBankAccountsData();

    function _refreshBankAccountsData() {
        $http({
            method: 'GET',
            url: '/clients/' + $window.localStorage.getItem('client') + '/bank-accounts',
            headers: {
                'Authorization': 'Bearer ' + $window.localStorage.getItem('token')
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

    $scope.redirectToTransactions = function (number) {
        var href = 'transactions.html';
        $window.localStorage.setItem('accountNumber', number);
        $window.location.href = href;
    }

});

