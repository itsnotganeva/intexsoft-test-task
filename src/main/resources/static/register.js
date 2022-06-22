var app = angular.module("Registration", []);

app.controller("AuthController", function ($scope, $http, $location, $window) {

    $scope.users=[];
    $scope.registrationRequest={
        login: "",
        name: "",
        surname: "",
        type: "",
        password: ""
    };

    $scope.register = function() {

        var method = "";
        var url = "";

        method = "POST";
        url = '/register';

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.registrationRequest),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(_error);
    };

    $scope.redirectToAuth = function () {
        var href = 'auth.html';
        $window.location.href = href;
    }

    function _error(res) {
        var data = res.data;
        var status = res.status;
        var header = res.header;
        var config = res.config;
        console.log("Error: " + res.headers + " : " + res.data);
        alert("Error: " + status + ":" + data);
    }

    // $scope.auth = function() {
    //
    //     var method = "";
    //     var url = "";
    //
    //     method = "POST";
    //     url = '/auth';
    //
    //     $http({
    //         method: method,
    //         url: url,
    //         data: angular.toJson($scope.registrationRequest),
    //         headers: {
    //             'Content-Type': 'application/json'
    //         }
    //     }).then(_error);
    // };
    //
    // $scope.redirectToAccounts = function () {
    //     var href = 'accounts.html';
    //     $window.location.href = href;
    // }
});
