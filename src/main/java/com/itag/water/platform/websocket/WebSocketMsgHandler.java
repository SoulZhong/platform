/**
 * 
 */
package com.itag.water.platform.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author soul
 *
 */
public class WebSocketMsgHandler extends TextWebSocketHandler {
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message ){
		
	}

}
