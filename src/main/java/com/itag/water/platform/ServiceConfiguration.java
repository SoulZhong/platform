/**
 * 
 */
package com.itag.water.platform;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Soul
 * @email soullleo@hotmail.com
 */
public class ServiceConfiguration {

	private Properties properties = new Properties();
	private int port;
	private String jdbcFile;

	public ServiceConfiguration() throws IOException {
		properties.load(this.getClass().getClassLoader()
				.getResourceAsStream("configuration.properties"));
		this.port = Integer.valueOf(properties.getProperty("port"));
		this.jdbcFile = properties.getProperty("jdbc.file");
	}

	public int getPort() {
		return port;
	}

	public String getJdbcFile() {

		return jdbcFile;
	}

}
