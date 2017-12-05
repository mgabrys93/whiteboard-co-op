package com.example.app.whiteboardcoop.model;

import java.awt.*;

public class Pixel {

    private String color;
    private int x;
    private int y;
    private int offsetX;
    private int offsetY;
    private int lineWidth;
    private String lineCap;

    public Pixel() {
    }

    public Pixel(String color, int x, int y, int offsetX, int offsetY, int lineWidth, String lineCap) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.lineWidth = lineWidth;
        this.lineCap = lineCap;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public String getLineCap() {
        return lineCap;
    }

    public void setLineCap(String lineCap) {
        this.lineCap = lineCap;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static Color getColor(String color){
        return Color.decode(color);
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
}
