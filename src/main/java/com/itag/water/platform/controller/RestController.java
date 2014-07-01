/**
 * 
 */
package com.itag.water.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itag.water.platform.data.StationInfos;

/**
 * @author Soul
 * @date Jun 22, 2014
 */
@Controller
@RequestMapping("/api/")
public class RestController {

	@Autowired
	private StationInfos stationInfos;

	@RequestMapping("status.w")
	public String status(ModelMap map) {
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> status.w <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ");
		map.put("stations", stationInfos.getStations());
		return "api/status";
	}

}
