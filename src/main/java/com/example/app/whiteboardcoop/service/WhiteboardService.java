package com.example.app.whiteboardcoop.service;

import com.example.app.whiteboardcoop.model.User;
import com.example.app.whiteboardcoop.model.Whiteboard;

import java.util.List;

public interface WhiteboardService extends DefaultService<Whiteboard> {

    List<Whiteboard> getNewestInactiveWhiteboards(int page);
    List<Whiteboard> getNewestActiveWhiteboards(int page);
    List<Whiteboard> getNewestInactiveWhiteboardsByUser(User user, int page);
    List<Whiteboard> getNewestActiveWhiteboardsByUser(User user, int page);
    int getMaxActivePagesNumber();
    int getMaxInactivePagesNumber();
    int getMaxActivePagesNumberPerUser(User user);
    int getMaxInactivePagesNumberPerUser(User user);
}
