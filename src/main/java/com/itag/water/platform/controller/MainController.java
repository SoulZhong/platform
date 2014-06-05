/**
 * 
 */
package com.itag.water.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itag.water.platform.dao.StationInfos;
import com.itag.water.platform.domain.Station;

/**
 * @author Soul
 * @date Jun 5, 2014
 */
@Controller
public class MainController {

	private StationInfos infos = StationInfos.instance;

	@RequestMapping("/")
	public String status(ModelMap map) {
		map.put("stations", infos.getStations());

		return "status";
	}

	@RequestMapping("/action/{stationId}")
	public String action(@PathVariable("stationId") int stationId, @RequestParam("no") int no,
			@RequestParam("type") int actionType) {

		Station station = infos.getStationInfo(stationId);

		System.out.println(stationId + "::stationId::" + station.getStationId());
		System.out.println("no:" + no);
		System.out.println("type" + actionType);

		if (actionType == Station.TriggerOn) {
			station.on(no);
		} else if (actionType == Station.TriggerOff) {
			station.off(no);
		}

		return "action";
	}

}
