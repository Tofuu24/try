package com.gabriel.draw.controller;

import com.gabriel.draw.command.SetPropertyCommand;
import com.gabriel.drawfx.command.CommandService;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.property.event.PropertyEventAdapter;
import com.gabriel.property.property.Property;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class PropertyEventListener extends PropertyEventAdapter {
    private AppService appService;
    private Runnable repaintCallback;

    public PropertyEventListener(AppService appService) {
        this.appService = appService;
    }
    
    public PropertyEventListener(AppService appService, Runnable repaintCallback) {
        this.appService = appService;
        this.repaintCallback = repaintCallback;
    }

    @Override
    public void onPropertyUpdated(Property property) {
        Shape selectedShape = appService.getSelectedShape();
        
        if (property.getName().equals("Fill color")) {
            Color newValue = (Color) property.getValue();
            if (selectedShape != null) {
                SetPropertyCommand<Color> cmd = new SetPropertyCommand<>("Fill color", 
                    selectedShape::getFill, selectedShape::setFill, newValue, repaintCallback);
                CommandService.ExecuteCommand(cmd);
            } else {
                appService.setFill(newValue);
            }
        } else if (property.getName().equals("Fore color")) {
            Color newValue = (Color) property.getValue();
            if (selectedShape != null) {
                SetPropertyCommand<Color> cmd = new SetPropertyCommand<>("Fore color", 
                    selectedShape::getColor, selectedShape::setColor, newValue, repaintCallback);
                CommandService.ExecuteCommand(cmd);
            } else {
                appService.setColor(newValue);
            }
        } else if (property.getName().equals("X Location")) {
            int newValue = (int) property.getValue();
            if (selectedShape != null) {
                SetPropertyCommand<Integer> cmd = new SetPropertyCommand<>("X Location", 
                    () -> selectedShape.getLocation().x, 
                    val -> selectedShape.setLocation(new java.awt.Point(val, selectedShape.getLocation().y)), 
                    newValue, repaintCallback);
                CommandService.ExecuteCommand(cmd);
            } else {
                appService.setXLocation(newValue);
            }
        } else if (property.getName().equals("Y Location")) {
            int newValue = (int) property.getValue();
            if (selectedShape != null) {
                SetPropertyCommand<Integer> cmd = new SetPropertyCommand<>("Y Location", 
                    () -> selectedShape.getLocation().y, 
                    val -> selectedShape.setLocation(new java.awt.Point(selectedShape.getLocation().x, val)), 
                    newValue, repaintCallback);
                CommandService.ExecuteCommand(cmd);
            } else {
                appService.setYLocation(newValue);
            }
        } else if (property.getName().equals("Width")) {
            int newValue = (int) property.getValue();
            if (selectedShape != null) {
                SetPropertyCommand<Integer> cmd = new SetPropertyCommand<>("Width", 
                    selectedShape::getWidth, selectedShape::setWidth, newValue, repaintCallback);
                CommandService.ExecuteCommand(cmd);
            } else {
                appService.setWidth(newValue);
            }
        } else if (property.getName().equals("Height")) {
            int newValue = (int) property.getValue();
            if (selectedShape != null) {
                SetPropertyCommand<Integer> cmd = new SetPropertyCommand<>("Height", 
                    selectedShape::getHeight, selectedShape::setHeight, newValue, repaintCallback);
                CommandService.ExecuteCommand(cmd);
            } else {
                appService.setHeight(newValue);
            }
        } else if (property.getName().equals("Line Thickness")) {
            int newValue = (int) property.getValue();
            if (selectedShape != null) {
                SetPropertyCommand<Integer> cmd = new SetPropertyCommand<>("Line Thickness", 
                    selectedShape::getThickness, selectedShape::setThickness, newValue, repaintCallback);
                CommandService.ExecuteCommand(cmd);
            } else {
                appService.setThickness(newValue);
            }
        } else if (property.getName().equals("Text")) {
            String newValue = (String) property.getValue();
            if (selectedShape != null) {
                SetPropertyCommand<String> cmd = new SetPropertyCommand<>("Text", 
                    selectedShape::getText, selectedShape::setText, newValue, repaintCallback);
                CommandService.ExecuteCommand(cmd);
            } else {
                appService.setText(newValue);
            }
        } else if (property.getName().equals("Start color")) {
            Color newValue = (Color) property.getValue();
            if (selectedShape != null) {
                SetPropertyCommand<Color> cmd = new SetPropertyCommand<>("Start color", 
                    selectedShape::getStartColor, selectedShape::setStartColor, newValue, repaintCallback);
                CommandService.ExecuteCommand(cmd);
            } else {
                appService.setStartColor(newValue);
            }
        } else if (property.getName().equals("End color")) {
            Color newValue = (Color) property.getValue();
            if (selectedShape != null) {
                SetPropertyCommand<Color> cmd = new SetPropertyCommand<>("End color", 
                    selectedShape::getEndColor, selectedShape::setEndColor, newValue, repaintCallback);
                CommandService.ExecuteCommand(cmd);
            } else {
                appService.setEndColor(newValue);
            }
        } else if (property.getName().equals("IsGradient")) {
            Boolean newValue = (Boolean) property.getValue();
            if (selectedShape != null) {
                SetPropertyCommand<Boolean> cmd = new SetPropertyCommand<>("IsGradient", 
                    selectedShape::isGradient, selectedShape::setGradient, newValue, repaintCallback);
                CommandService.ExecuteCommand(cmd);
            } else {
                appService.setIsGradient(newValue);
            }
        } else if (property.getName().equals("IsVisible")) {
            Boolean newValue = (Boolean) property.getValue();
            if (selectedShape != null) {
                SetPropertyCommand<Boolean> cmd = new SetPropertyCommand<>("IsVisible", 
                    selectedShape::isVisible, selectedShape::setVisible, newValue, repaintCallback);
                CommandService.ExecuteCommand(cmd);
            } else {
                appService.setIsVisible(newValue);
            }
        } else if (property.getName().equals("Font family")) {
            Font font = selectedShape != null ? selectedShape.getFont() : appService.getFont();
            if (font == null) font = new Font("Arial", Font.PLAIN, 12);
            Font newFont = new Font((String) property.getValue(), font.getStyle(), font.getSize());
            if (selectedShape != null) {
                SetPropertyCommand<Font> cmd = new SetPropertyCommand<>("Font", 
                    selectedShape::getFont, selectedShape::setFont, newFont, repaintCallback);
                CommandService.ExecuteCommand(cmd);
            } else {
                appService.setFont(newFont);
            }
        } else if (property.getName().equals("Font style")) {
            Font font = selectedShape != null ? selectedShape.getFont() : appService.getFont();
            if (font == null) font = new Font("Arial", Font.PLAIN, 12);
            Font newFont = new Font(font.getFamily(), (int)property.getValue(), font.getSize());
            if (selectedShape != null) {
                SetPropertyCommand<Font> cmd = new SetPropertyCommand<>("Font", 
                    selectedShape::getFont, selectedShape::setFont, newFont, repaintCallback);
                CommandService.ExecuteCommand(cmd);
            } else {
                appService.setFont(newFont);
            }
        } else if (property.getName().equals("Font size")) {
            Font font = selectedShape != null ? selectedShape.getFont() : appService.getFont();
            if (font == null) font = new Font("Arial", Font.PLAIN, 12);
            Font newFont = new Font(font.getFamily(), font.getStyle(), (int) property.getValue());
            if (selectedShape != null) {
                SetPropertyCommand<Font> cmd = new SetPropertyCommand<>("Font", 
                    selectedShape::getFont, selectedShape::setFont, newFont, repaintCallback);
                CommandService.ExecuteCommand(cmd);
            } else {
                appService.setFont(newFont);
            }
        }
    }
}