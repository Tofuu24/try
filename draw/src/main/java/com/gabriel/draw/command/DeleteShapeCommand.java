package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

public class DeleteShapeCommand implements Command {
    private final Shape shape;
    private final AppService appService;
    private final Runnable repaintCallback;

    public DeleteShapeCommand(AppService appService, Shape shape, Runnable repaintCallback) {
        this.shape = shape;
        this.appService = appService;
        this.repaintCallback = repaintCallback;
    }

    @Override
    public void execute() {
        appService.delete(shape);
        if (repaintCallback != null) {
            repaintCallback.run();
        }
    }

    @Override
    public void undo() {
        appService.create(shape);
        if (repaintCallback != null) {
            repaintCallback.run();
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
