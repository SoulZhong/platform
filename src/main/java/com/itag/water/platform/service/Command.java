/**
 * 
 */
package com.itag.water.platform.service;

/**
 * @author Soul
 * @date Jun 5, 2014
 */
public enum Command {
	// 响应: ! 表示动作成功，失败重试3次

	MainON(1, "$s1action"), // 主泵启动
	MainOff(1, "$s2action"), // 主泵关闭
	SecondaryOn(2, "$s3action"), // 辅泵启动
	SecondaryOff(2, "$s4action");// 辅泵关闭

	private String command;
	private int number;

	Command(int number, String command) {
		this.number = number;
		this.command = command;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
