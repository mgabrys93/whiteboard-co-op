package com.example.app.whiteboardcoop.dto;

import com.example.app.whiteboardcoop.model.Whiteboard;

import java.time.format.DateTimeFormatter;

public class WhiteboardDto {

    private long id;
    private String ownerUsername;
    private String creationDateTime;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public WhiteboardDto() {
    }

    public WhiteboardDto(Whiteboard whiteboard){
        this.id = whiteboard.getId();
        this.creationDateTime = whiteboard.getCreationDateTime().format(dateTimeFormatter);
        this.ownerUsername = whiteboard.getOwner().getUsername();
    }

    public long getId() {
        return id;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public String getCreationDateTime() {
        return creationDateTime;
    }

}
