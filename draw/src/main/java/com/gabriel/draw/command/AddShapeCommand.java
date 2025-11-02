package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

public class AddShapeCommand implements Command {
    private final Shape shape;
    private final AppService appService;
    private final Runnable repaintCallback;

    public AddShapeCommand(AppService appService, Shape shape, Runnable repaintCallback) {
        this.shape = shape;
        this.appService = appService;
        this.repaintCallback = repaintCallback;
    }

    @Override
    public void execute() {
        appService.create(shape);
        if (repaintCallback != null) {
            repaintCallback.run();
        }
    }

    @Override
    public void undo() {
        appService.delete(shape);
        if (repaintCallback != null) {
            repaintCallback.run();
        }
    }

    @Override
    public void redo() {
        execute();
    }
}