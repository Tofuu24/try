package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;
import java.awt.Point;

public class MoveShapeCommand implements Command {
    private final Shape shape;
    private final AppService appService;
    private final Point startPoint;
    private final Point endPoint;
    private final Runnable repaintCallback;

    public MoveShapeCommand(AppService appService, Shape shape, Point startPoint, Point endPoint, Runnable repaintCallback) {
        this.shape = shape;
        this.appService = appService;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.repaintCallback = repaintCallback;
    }

    @Override
    public void execute() {
        appService.move(shape, startPoint, endPoint);
        if (repaintCallback != null) {
            repaintCallback.run();
        }
    }

    @Override
    public void undo() {
        appService.move(shape, endPoint, startPoint);
        if (repaintCallback != null) {
            repaintCallback.run();
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
