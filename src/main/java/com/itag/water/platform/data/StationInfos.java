/**
 * 
 */
package com.itag.water.platform.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.itag.water.platform.domain.DataFrame;
import com.itag.water.platform.domain.Station;

/**
 * @author Soul
 * @email soullleo@hotmail.com
 */
public class StationInfos {

	private List<StationListener> listeners = new ArrayList<StationListener>();

	private Map<Integer, Station> stations = new ConcurrentHashMap<Integer, Station>();

	public void updateInfo(int stationId, DataFrame dataFrame) {

		Station station = stations.get(stationId);
		if (station == null) {
			station = new Station(stationId);
			stations.put(stationId, station);
		}
		station.setLastDataFrame(dataFrame);

		onUpdateInfo(station);
	}

	private void onUpdateInfo(Station station) {
		for (int i = stations.size() - 1; i >= 0; i--) {
			listeners.get(i).onUpdateInfo();
		}

	}

	public Station getStationInfo(int stationId) {
		return stations.get(stationId);
	}

	public Map<Integer, Station> getStations() {
		return stations;
	}

	public Station getStationInfo(String ip) {
		for (Station station : stations.values()) {
			if (ip.equals(station.getLastDataFrame().getIp())) {
				return station;
			}
		}

		return null;
	}
}
