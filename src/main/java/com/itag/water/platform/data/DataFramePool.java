/**
 * 
 */
package com.itag.water.platform.data;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.itag.water.platform.dao.DataFrameDao;
import com.itag.water.platform.domain.DataFrame;

/**
 * @author Soul
 * @date Jun 12, 2014
 */
public class DataFramePool {

	private Logger logger = LogManager.getLogger(DataFramePool.class);

	@Autowired
	private DataFrameDao dataFrameDao;

	private Map<Integer, ConcurrentLinkedQueue<DataFrame>> pool = new ConcurrentHashMap<Integer, ConcurrentLinkedQueue<DataFrame>>();

	public void save() {
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		logger.info(" ========== save task run ========== ");

		int voltageSum = 0;
		int waterGageSum = 0;
		int waterLevelSum = 0;
		int electricitySum = 0;

		for (Entry<Integer, ConcurrentLinkedQueue<DataFrame>> entry : pool.entrySet()) {

			ConcurrentLinkedQueue<DataFrame> dataFrames = entry.getValue();

			logger.info(" -- before saving -- stationId:" + entry.getKey() + ", size:" + dataFrames.size());

			pool.put(entry.getKey(), new ConcurrentLinkedQueue<DataFrame>());

			for (DataFrame dataFrame : dataFrames) {
				voltageSum += dataFrame.getVoltage();
				waterGageSum += dataFrame.getWaterGage();
				waterLevelSum += dataFrame.getWaterLevel();
				electricitySum += dataFrame.getElectricity();
			}
			double size = dataFrames.size();
			DataFrame last = dataFrames.peek();
			last.setVoltage(voltageSum / size);
			last.setWaterGage(waterGageSum / size);
			last.setWaterLevel(waterLevelSum / size);
			last.setElectricity(electricitySum / size);
			last.setCount((int) size);

			dataFrameDao.save(last);

			logger.info(" -- after saved -- stationId:" + entry.getKey() + ", size:" + pool.get(entry.getKey()).size());
		}

		logger.info(" ========== save task finished ========== ");
	}

	public void add(DataFrame dataFrame) {
		ConcurrentLinkedQueue<DataFrame> queue = pool.get(dataFrame.getStationId());
		if (queue == null) {// Double check
			synchronized (pool) {
				queue = pool.get(dataFrame.getStationId());
				if (queue == null) {
					queue = new ConcurrentLinkedQueue<DataFrame>();
					pool.put(dataFrame.getStationId(), queue);
				}
			}
		}
		queue.add(dataFrame);
	}
}
