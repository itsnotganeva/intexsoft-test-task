var app = angular.module("BankSystem", []);

app.controller("BankAccountController", function ($scope, $http) {

    $scope.bankAccounts=[];
    $scope.bankAccountsDto={
        id: "",
        number: "",
        currency: "",
        amountOfMoney: "",
        owner: "",
        bankProducer: ""
    };

    _refreshBankAccountsData();

    function _refreshBankAccountsData() {
        $http({
            method: 'GET',
            url: '/clients/2/bank-accounts'
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
