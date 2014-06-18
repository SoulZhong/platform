/**
 * 
 */
package com.itag.water.platform.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.itag.water.platform.domain.DataFrame;
import com.itag.water.platform.domain.Station;

/**
 * @author Soul
 * @email soullleo@hotmail.com
 */
public class StationInfos {

	public static StationInfos instance = new StationInfos();

	private Map<Integer, Station> stations = new ConcurrentHashMap<Integer, Station>();

	public void updateInfo(int stationId, DataFrame dataFrame) {

		Station station = stations.get(stationId);
		if (station == null) {
			station = new Station(stationId);
			stations.put(stationId, station);
		}
		station.setLastDataFrame(dataFrame);

	}

	public Station getStationInfo(int stationId) {
		return stations.get(stationId);
	}

	public Map<Integer, Station> getStations() {
		return stations;
	}

}
