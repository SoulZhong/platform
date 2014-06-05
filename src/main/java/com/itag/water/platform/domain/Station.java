/**
 * 
 */
package com.itag.water.platform.domain;

import java.net.DatagramPacket;
import java.net.InetAddress;

import com.itag.water.platform.service.Command;
import com.itag.water.platform.service.UdpSender;

/**
 * @author Soul
 * @email soullleo@hotmail.com
 */
public class Station {

	private static final UdpSender udpSender = UdpSender.instance;

	public static final int TriggerOn = 1;

	public static final int TriggerOff = -1;

	private long id;

	private final int stationId;
	private volatile String name;
	private volatile String description;

	private volatile boolean enable;

	private volatile DataFrame lastDataFrame;

	public Station(int stationId) {
		this.stationId = stationId;

		this.enable = true;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getStationId() {
		return stationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public DataFrame getLastDataFrame() {
		return lastDataFrame;
	}

	public void setLastDataFrame(DataFrame lastDataFrame) {
		this.lastDataFrame = lastDataFrame;
	}

	public void on(int number) {

		System.out.println("nummmmm:" + number);
		try {
			DatagramPacket msg = null;
			if (number == Command.MainON.getNumber()) {
				byte[] v = Command.MainON.getCommand().getBytes();
				msg = new DatagramPacket(v, v.length, InetAddress.getByName(this.lastDataFrame.getIp()),
						this.lastDataFrame.getPort());
			} else if (number == Command.SecondaryOn.getNumber()) {
				byte[] v = Command.SecondaryOn.getCommand().getBytes();
				msg = new DatagramPacket(v, v.length, InetAddress.getByName(this.lastDataFrame.getIp()),
						this.lastDataFrame.getPort());
			}

			System.out.println("datagrampacket:" + msg);
			if (msg != null) {
				udpSender.send(msg);
			} else {
				// TODO
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void off(int number) {
		try {

			DatagramPacket msg = null;
			if (number == Command.MainOff.getNumber()) {
				byte[] v = Command.MainOff.getCommand().getBytes();
				msg = new DatagramPacket(v, v.length, InetAddress.getByName(this.lastDataFrame.getIp()),
						this.lastDataFrame.getPort());
			} else if (number == Command.SecondaryOff.getNumber()) {
				byte[] v = Command.SecondaryOff.getCommand().getBytes();
				msg = new DatagramPacket(v, v.length, InetAddress.getByName(this.lastDataFrame.getIp()),
						this.lastDataFrame.getPort());
			}

			if (msg != null) {
				udpSender.send(msg);
			} else {
				// TODO

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}