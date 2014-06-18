/**
 * 
 */
package com.itag.water.platform.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.itag.water.platform.domain.Action;
import com.itag.water.platform.domain.Station;

/**
 * @author Soul
 * @date Jun 18, 2014
 */
public class Actions extends Thread {

	@Autowired
	private StationInfos stationInfos;

	private Map<Integer, Action> actions = new HashMap<Integer, Action>();

	public void put(int stationId, Action action) {
		synchronized (actions) {
			actions.put(stationId, action);
			actions.notify();
		}
	}

	@Override
	public void run() {

		while (true) {
			synchronized (actions) {
				if (actions.isEmpty()) {
					try {
						actions.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					for (Entry<Integer, Action> entry : actions.entrySet()) {
						Station station = stationInfos.getStationInfo(entry.getKey());

						Action action = entry.getValue();
						if (action.isTurnOn()) {
							station.on(action.getButtonNumber());
						} else {
							station.off(action.getButtonNumber());
						}
					}

				}
			}

		}
	}

}
