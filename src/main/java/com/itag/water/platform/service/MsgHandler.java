/**
 * 
 */
package com.itag.water.platform.service;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.itag.water.platform.dao.DataFrameDao;
import com.itag.water.platform.data.Actions;
import com.itag.water.platform.data.DataFramePool;
import com.itag.water.platform.data.StationInfos;
import com.itag.water.platform.domain.DataFrame;
import com.itag.water.platform.exception.IllegalDataFrameException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

/**
 * @author Soul
 * @date May 21, 2014
 */
public class MsgHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	private Logger logger = LogManager.getLogger(MsgHandler.class);

	@Autowired
	private Actions actions;

	@Autowired
	private StationInfos stationInfos;

	@Autowired
	private DataFrameDao dataFrameDao;

	@Autowired
	private DataFramePool dataFramePool;

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) {

		ByteBuf bb = msg.content();

		byte[] bytes = new byte[bb.readableBytes()];
		int readerIndex = bb.readerIndex();
		bb.getBytes(readerIndex, bytes);

		try {

			if (isActionReply(bytes)) {
				actions.hasReplied(msg.sender().getAddress().getHostAddress());
			} else {

				validate(bytes);
				DataFrame dataFrame = parseData(msg, bytes);
				
				dataFrame.setCtx(ctx);

				// dataFrameDao.save(dataFrame);// save to database
				dataFramePool.add(dataFrame);

				stationInfos.updateInfo(dataFrame.getStationId(), dataFrame);// update
				// stationInfo

				StringBuffer tmp = byteArrayToString(bytes);
				logger.info(dataFrame + ", byte array:" + tmp);

//				System.out.println(dataFrame + ", byte array:" + tmp);

				DatagramPacket reply = new DatagramPacket(Unpooled.copiedBuffer("OK", Charset.forName("UTF-8")),
						msg.sender());
				ctx.write(reply);// reply
			}
		} catch (IllegalDataFrameException e) {

			StringBuffer tmp = byteArrayToString(bytes);
			logger.error(e.getMessage() + ",data recieved: " + tmp);

			// DATAGRAMPACKET REPLY = NEW DATAGRAMPACKET(UNPOOLED.COPIEDBUFFER(
			// "ERROR", CHARSET.FORNAME("UTF-8")), MSG.SENDER());
			//
			// CTX.WRITE(REPLY);
		}

	}

	/**
	 * 是否是回复信息
	 * 
	 * @param bytes
	 * @return
	 */
	private boolean isActionReply(byte[] bytes) {
		return "!".equals(new String(bytes));
	}

	private StringBuffer byteArrayToString(byte[] bytes) {
		StringBuffer tmp = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			tmp.append(Integer.toHexString((bytes[i] & 0xff)) + ",");
		}
		return tmp;
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	private void validate(byte[] bytes) throws IllegalDataFrameException {
		if (bytes.length < 12) {
			throw new IllegalDataFrameException("data is uncompleted");
		} else if (bytes[0] != 64 || bytes[11] != 64) {
			throw new IllegalDataFrameException("illegall header or tail byte");
		}
	}

	/**
	 * @param msg
	 * @param bytes
	 * @return
	 */
	private DataFrame parseData(DatagramPacket msg, byte[] bytes) {

		DataFrame dataFrame = new DataFrame();

		InetSocketAddress sender = msg.sender();

		dataFrame.setIp(sender.getAddress().getHostAddress());
		dataFrame.setPort(sender.getPort());

		dataFrame.setTime(new Date());

		dataFrame.setStationId(bytes[1] & 0xff);
		dataFrame.setState(bytes[2] & 0xff);

		double voltage = ((bytes[3] & 0xff) << 8) + (bytes[4] & 0xff);
		dataFrame.setVoltage(voltage);

		double electricity = ((bytes[5] & 0xff) << 8) + (bytes[6] & 0xff);
		dataFrame.setElectricity(electricity);

		double waterGage = ((bytes[7] & 0xff) << 8) + (bytes[8] & 0xff);
		dataFrame.setWaterGage(waterGage);

		double waterLevel = ((bytes[9] & 0xff) << 8) & +(bytes[10] & 0xff);
		dataFrame.setWaterLevel(waterLevel);
		return dataFrame;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		cause.printStackTrace();
	}
}
