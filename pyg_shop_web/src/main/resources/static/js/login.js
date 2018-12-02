app.controller('loginController', function ($scope, $http) {

    $scope.form = {
        username: null,
        password: null,
    };

    $scope.login = function () {

        var data = $scope.form;
        alert(data);
        $http.post("/authentication/form", data).success(
            function (response) {
                alert(222);
                if (response.success) {
                    $location.path("/admin/index.html");
                } else {
                    $scope.msg = response.extend.message;
                }
            }).error(
                function (data) {
                    alert(333);
                    alert(data)
                }
        )
    }

    $scope.sendSms = function () {
        $http.get("/code/sms?mobile=" + $("#mobile").val());
    }
})