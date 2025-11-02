package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import java.awt.Point;

public class ScaleShapeCommand implements Command {
    private final Shape shape;
    private final Point oldLocation;
    private final int oldWidth;
    private final int oldHeight;
    private final Point newLocation;
    private final int newWidth;
    private final int newHeight;
    private final Runnable repaintCallback;

    public ScaleShapeCommand(Shape shape, Point oldLocation, int oldWidth, int oldHeight, Point newLocation, int newWidth, int newHeight, Runnable repaintCallback) {
        this.shape = shape;
        this.oldLocation = oldLocation;
        this.oldWidth = oldWidth;
        this.oldHeight = oldHeight;
        this.newLocation = newLocation;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
        this.repaintCallback = repaintCallback;
    }

    @Override
    public void execute() {
        shape.setLocation(newLocation);
        shape.setWidth(newWidth);
        shape.setHeight(newHeight);
        if (repaintCallback != null) {
            repaintCallback.run();
        }
    }

    @Override
    public void undo() {
        shape.setLocation(oldLocation);
        shape.setWidth(oldWidth);
        shape.setHeight(oldHeight);
        if (repaintCallback != null) {
            repaintCallback.run();
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
