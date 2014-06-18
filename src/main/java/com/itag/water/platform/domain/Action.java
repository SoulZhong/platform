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

	private int stationId;
	private int buttonNumber;

	private boolean turnOn;

	public static final int ACTIONTYPE_ON = 1;
	public static final int ACTIONTYPE_OFF = -1;

	public Action(int stationId, int buttonNumber, int actionType) throws IllegalRequest {
		this.stationId = stationId;
		this.buttonNumber = buttonNumber;

		if (actionType == ACTIONTYPE_ON) {
			this.turnOn = true;
		} else if (actionType == ACTIONTYPE_OFF) {
			this.turnOn = false;
		} else {
			throw new IllegalRequest("wrong actiontype " + actionType);
		}

	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
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

}
