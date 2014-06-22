function StationCtrl($scope, $http) {
	$http.get('api/status.w').success(function(data) {
		$scope.stations = data;
	});
	
	$scope.command=function(stationId, num, type){
		console.log(stationId +',' + num + ',' + type);
		
		$.get("action.w?stationId="+stationId +"&no="+ num +"&type="+type, function(result){
		    console.log(result);
		  });

	};
}