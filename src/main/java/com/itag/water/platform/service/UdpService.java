/**
 * 
 */
package com.itag.water.platform.service;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.itag.water.platform.ServiceConfiguration;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * @author Soul
 * @date May 21, 2014
 */
public class UdpService {

	private Logger logger = LogManager.getLogger(UdpService.class);
	private ServiceConfiguration serviceConfig;

	@Autowired
	private MsgHandler msgHandler;

	public void start() throws IOException {
		serviceConfig = new ServiceConfiguration();

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		// new Exception().printStackTrace();

		new Thread() {
			@Override
			public void run() {

				System.out.println("start udp service");
				try {

					EventLoopGroup group = new NioEventLoopGroup();

					try {
						Bootstrap b = new Bootstrap();
						b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)
								.handler(msgHandler);

						b.bind(serviceConfig.getPort()).sync().channel().closeFuture().await();
					} catch (InterruptedException e) {
						// e.printStackTrace();
					} finally {
						group.shutdownGracefully();
					}
				} catch (Exception e) {
					logger.error("", e);
				}
			}
		}.start();

	}

}
