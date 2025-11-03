package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;
import java.awt.Color;
import java.awt.Font;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SetPropertyCommand<T> implements Command {
    private final Supplier<T> getter;
    private final Consumer<T> setter;
    private final T oldValue;
    private final T newValue;
    private final Runnable repaintCallback;
    private final String propertyName;

    public SetPropertyCommand(String propertyName, Supplier<T> getter, Consumer<T> setter, T newValue, Runnable repaintCallback) {
        this.propertyName = propertyName;
        this.getter = getter;
        this.setter = setter;
        this.oldValue = getter.get();
        this.newValue = newValue;
        this.repaintCallback = repaintCallback;
    }

    @Override
    public void execute() {
        setter.accept(newValue);
        if (repaintCallback != null) {
            repaintCallback.run();
        }
    }

    @Override
    public void undo() {
        setter.accept(oldValue);
        if (repaintCallback != null) {
            repaintCallback.run();
        }
    }

    @Override
    public void redo() {
        execute();
    }
    
    @Override
    public String toString() {
        return "Set " + propertyName + " to " + newValue;
    }
}
