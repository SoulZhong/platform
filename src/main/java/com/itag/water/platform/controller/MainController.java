/**
 * 
 */
package com.itag.water.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itag.water.platform.data.Actions;
import com.itag.water.platform.data.StationInfos;
import com.itag.water.platform.domain.Action;
import com.itag.water.platform.domain.Station;
import com.itag.water.platform.exception.IllegalRequest;

/**
 * @author Soul
 * @date Jun 5, 2014
 */
@Controller
public class MainController {

	@Autowired
	private StationInfos stationInfos;

	@Autowired
	private Actions actions;

	@RequestMapping("/status")
	public String status(ModelMap map) {
		map.put("stations", stationInfos.getStations());

		return "status";
	}

	@RequestMapping("/action/{stationId}")
	public String action(@PathVariable("stationId") int stationId, @RequestParam("no") int no,
			@RequestParam("type") int actionType) throws IllegalRequest {

		Action action = new Action(stationId, no, actionType);
		Station station = stationInfos.getStationInfo(stationId);

		actions.put(stationId, action);

		System.out.println(stationId + "::stationId::" + station.getStationId());
		System.out.println("no:" + no);
		System.out.println("type" + actionType);

		// if (actionType == Station.TriggerOn) {
		// station.on(no);
		// } else if (actionType == Station.TriggerOff) {
		// station.off(no);
		// }

		return "action";
	}

	public StationInfos getInfos() {
		return stationInfos;
	}

	public void setInfos(StationInfos infos) {
		this.stationInfos = infos;
	}

	public Actions getActions() {
		return actions;
	}

	public void setActions(Actions actions) {
		this.actions = actions;
	}

}
