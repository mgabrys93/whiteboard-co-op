package com.example.app.whiteboardcoop.listener;

import com.example.app.whiteboardcoop.model.Whiteboard;
import com.example.app.whiteboardcoop.service.WhiteboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class WebsocketListener {

    @Autowired
    private WhiteboardService whiteboardService;

    private Map<String,Long> activeWhiteboards = new HashMap<>();

    @EventListener
    public void handleSubscribeEvent(SessionSubscribeEvent sessionSubscribeEvent){
        String pattern = "/topic/whiteboard/[0-9]+/content/send";
        String rejectNonNumber = "[^0-9]+";
        String simpSessionId = sessionSubscribeEvent.getMessage().getHeaders().get("simpSessionId").toString();
        String destination = sessionSubscribeEvent.getMessage().getHeaders().get("simpDestination").toString();
        if(destination.matches(pattern)){
            long whiteboardId = Long.parseLong(destination.replaceAll(rejectNonNumber, ""));
            activeWhiteboards.put(simpSessionId, whiteboardId);
            Optional<Whiteboard> whiteboard = whiteboardService.findOne(whiteboardId);
            whiteboard.get().setActive(true);
            whiteboardService.update(whiteboard.get());
        }
    }

    @EventListener
    public void handleDisconnectEvent(SessionDisconnectEvent sessionDisconnectEvent){
        String simpSessionId = sessionDisconnectEvent.getSessionId();
        if(activeWhiteboards.containsKey(simpSessionId)){
            long whiteboardId = activeWhiteboards.get(simpSessionId);
            activeWhiteboards.remove(simpSessionId);
            Optional<Whiteboard> whiteboard = whiteboardService.findOne(whiteboardId);
            whiteboard.get().setActive(false);
            whiteboardService.update(whiteboard.get());
        }
    }

    @PreDestroy
    public void cleanup(){
        for(String s : activeWhiteboards.keySet()){
            Optional<Whiteboard> whiteboard = whiteboardService.findOne(activeWhiteboards.get(s));
            if(whiteboard.isPresent()){
                whiteboard.get().setActive(false);
                whiteboardService.update(whiteboard.get());
            }
        }
    }

}
