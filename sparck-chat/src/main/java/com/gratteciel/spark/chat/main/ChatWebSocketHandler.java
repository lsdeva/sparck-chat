package com.gratteciel.spark.chat.main;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class ChatWebSocketHandler {
	private String sender, msg;
	
	@OnWebSocketConnect
	public void onCOnnect(Session user) throws Exception{
		String username = "User"+ Chat.nextUserNUmber++;
		Chat.userUserNameMap.put(user, username);
		Chat.broadcastMessage(sender = "server", msg = (username + "joined the chat"));
	}
	
    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        String username = Chat.userUserNameMap.get(user);
        Chat.userUserNameMap.remove(user);
        Chat.broadcastMessage(sender = "Server", msg = (username + " left the chat"));
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        Chat.broadcastMessage(sender = Chat.userUserNameMap.get(user), msg = message);
    }
	
}
