/**
 * 
 */
package com.itag.water.platform.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.itag.water.platform.domain.Action;
import com.itag.water.platform.domain.Station;

/**
 * @author Soul
 * @date Jun 18, 2014
 */
public class Actions {

	private Logger logger = LogManager.getLogger(Actions.class);

	@Autowired
	private StationInfos stationInfos;

	private Map<Station, Action> actions = new HashMap<Station, Action>();

	public void add(Action action) {
		synchronized (actions) {
			Station station = action.getStation();
			actions.put(station, action);
			actions.notify();
		}
	}

	public void run() {
		synchronized (actions) {
			if (actions.isEmpty()) {
				try {
					actions.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {

				for (Entry<Station, Action> entry : actions.entrySet()) {

					Action action = entry.getValue();

					if (action.doneMax()) {

						logger.error("fail to do action: " + action);
						actions.remove(entry.getKey());
					} else {
						action.doAction();
					}

				}

			}
		}
	}

	public void hasReplied(String ip) {

		Station station = stationInfos.getStationInfo(ip);
		if (station != null) {
			actions.remove(station);
		} else {
			logger.error("no station found for ip:" + ip);
		}

	}

}
