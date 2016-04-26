package com.gratteciel.spark.chat.main;

import org.eclipse.jetty.websocket.api.Session;
import org.json.*;


import java.text.*;
import java.util.*;
import static j2html.TagCreator.*;
import static spark.Spark.*;

public class Chat {

	static Map<Session, String> userUserNameMap = new HashMap<Session, String>();
	static int nextUserNUmber = 1;

	public static void main(String[] args) {
		staticFileLocation("public");
		webSocket("/chat", ChatWebSocketHandler.class);
		init();
	}

	public static void broadcastMessage(String sender, String message) {
		userUserNameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
			try {
				session.getRemote().sendString(String.valueOf(new JSONObject()
						.put("userMessage",createHtmlmessageFromSender(sender,message))
						.put("userList", userUserNameMap.values())
						));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private static String createHtmlmessageFromSender(String sender, String message) {
		return article().with(b(sender + "says:"), p(message), span().withClass("timestamp").withText(new SimpleDateFormat("HH:mm:ss").format(new Date()))).render();
	}
}
