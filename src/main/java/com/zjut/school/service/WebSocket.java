package com.zjut.school.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 张璐杰
 * 2018/4/22 17:19
 */


@Component
@ServerEndpoint("/webSocket/{userId}")
@Slf4j
public class WebSocket {

    private static int onlineCount = 0;

    private static ConcurrentHashMap<String, WebSocket> webSocketSet = new ConcurrentHashMap<>();

    private Session session;

    private String userId = "";

    @OnOpen
    public void onOpen(@PathParam("userId") String param,
                       Session session,
                       EndpointConfig config) {
        userId = param;
        this.session = session;
        webSocketSet.put(param, this);
        addOnlineCount();
       // log.info("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    @OnClose
    public void onClose() {
        if (!userId.equals("")) {
            webSocketSet.remove(userId);
            subOnlineCount();
          //  log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        //log.info("来自客户端的消息:" + message);
    }

    public void sendToUser(String receiver, String message) {
        try {
            if (webSocketSet.get(receiver) != null) {
                webSocketSet.get(receiver).session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendAll(List<String> receivers, String message) {
        for (String receiver : receivers) {
            try {
                if (webSocketSet.get(receiver) != null) {
                    webSocketSet.get(receiver).session.getBasicRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }
}
