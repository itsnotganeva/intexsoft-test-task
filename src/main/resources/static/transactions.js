var app = angular.module("Transactions", []);

app.controller("TransactionController", function ($scope, $http, $window) {

    $scope.transactions=[];

    getTransactions();

    function getTransactions() {
        $http({
            method: 'GET',
            url: '/transactions/' + $window.localStorage.getItem('accountNumber') + '/all',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + $window.localStorage.getItem('token')
            }
        }).then(
            function(res) {
                $scope.transactions = res.data;
            }
        );
    }

});

