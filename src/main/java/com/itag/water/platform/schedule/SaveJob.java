/**
 * 
 */
package com.itag.water.platform.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author Soul
 * @date Jun 12, 2014
 */
public class SaveJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		

			System.out.println("Job runing>>>>>>>>>>>>>>>>");

	}

}
