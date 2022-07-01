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
        $http({
            method: 'POST',
            url: '/register',
            data: angular.toJson($scope.registrationRequest),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(status);
    };

    $scope.redirectToAuth = function () {
        var href = 'index.html';
        $window.location.href = href;
    }

    function status(res) {
        var status = res.status;
        alert("Status: " + status);
        $scope.redirectToAuth();
    }
});
