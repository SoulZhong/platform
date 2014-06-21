function StationCtrl($scope, $http) {
	$http.get('api/status.w').success(function(data) {
		$scope.stations = data;
	});
}