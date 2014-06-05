/**
 * 
 */
package com.itag.water.platform.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @author Soul
 * @date Jun 5, 2014
 */
public class UdpSender {

	public static final UdpSender instance = new UdpSender();

	private DatagramSocket socket;

	private UdpSender() {
		try {
			socket = new DatagramSocket(9999);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void send(DatagramPacket msg) throws IOException {
		
		System.out.println("msg :::: "+msg);
		socket.send(msg);
	}

}
