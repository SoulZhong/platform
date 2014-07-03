/**
 * 
 */
package com.itag.water.platform.domain;

import com.itag.water.platform.exception.IllegalRequest;

/**
 * @author Soul
 * @date Jun 18, 2014
 */
public class Action {

	private Station station;
	private int buttonNumber;

	private boolean turnOn;
	private int doCounter;

	public static final int ACTIONTYPE_ON = 1;
	public static final int ACTIONTYPE_OFF = -1;
	private static final int DONE_MAX = 3;

	@Override
	public String toString() {

		return "Station: " + station.getStationId() + ", StationAddress: " + station.getLastDataFrame().getIp() + ":"
				+ station.getLastDataFrame().getPort() + ", buttonNumber: " + buttonNumber + ", turnOn: " + turnOn
				+ ", executeCount: " + doCounter;
	}

	public Action(Station station, int buttonNumber, int actionType) throws IllegalRequest {
		this.station = station;
		this.buttonNumber = buttonNumber;

		if (actionType == ACTIONTYPE_ON) {
			this.turnOn = true;
		} else if (actionType == ACTIONTYPE_OFF) {
			this.turnOn = false;
		} else {
			throw new IllegalRequest("wrong actiontype " + actionType);
		}

	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public int getButtonNumber() {
		return buttonNumber;
	}

	public void setButtonNumber(int buttonNumber) {
		this.buttonNumber = buttonNumber;
	}

	public boolean isTurnOn() {
		return turnOn;
	}

	public void setTurnOn(boolean turnOn) {
		this.turnOn = turnOn;
	}

	public void doAction() {

		if (isTurnOn()) {
			station.on(buttonNumber);
		} else {
			station.off(buttonNumber);
		}

		doCounter++;

	}

	public boolean doneMax() {

		return doCounter > DONE_MAX;
	}

}
