package com.example.app.whiteboardcoop.controller;

import com.example.app.whiteboardcoop.model.Pixel;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/whiteboard")
public class WhiteboardWebsocketController {

    @MessageMapping("/hello/whiteboard/{whiteboardId}")
    @SendTo("/topic/whiteboard/{whiteboardId}")
    public Pixel pixelTransmission(@DestinationVariable Long whiteboardId, Pixel pixel){
        return pixel;
    }

    @MessageMapping("/hello/whiteboard/{whiteboardId}/content/send")
    @SendTo("/topic/whiteboard/{whiteboardId}/content/send")
    public String imageRequest(@DestinationVariable Long whiteboardId){
        return "";
    }

    @MessageMapping("/hello/whiteboard/{whiteboardId}/content")
    @SendTo("/topic/whiteboard/{whiteboardId}/content")
    public String imageSend(@DestinationVariable Long whiteboardId, String imageString){
        return imageString;
    }

}
