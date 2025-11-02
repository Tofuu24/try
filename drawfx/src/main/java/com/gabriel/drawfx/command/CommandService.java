package com.gabriel.drawfx.command;

import java.util.Stack;
public class CommandService {
    static Stack<Command> undoStack = new Stack<Command>();
    static Stack<Command> redoStack = new Stack<Command>();

    public static void ExecuteCommand(Command command) {
        command.execute();
        undoStack.push(command);
        // Clear redo stack whenever a new command is executed
        redoStack.clear();
    }

    public static void undo() {
        if (undoStack.empty())
            return;
        Command command = undoStack.pop();
        command.undo();
        redoStack.push(command);
    }

    /**
     * Register a command on the undo stack without executing it.
     * Use this when the model has already been updated (for example during drag)
     * and you just want to record the command so it can be undone/redone.
     */
    public static void register(Command command) {
        undoStack.push(command);
        // Clear redo stack whenever a new command is registered
        redoStack.clear();
    }

    public static boolean canRedo() {
        return !redoStack.empty();
    }

    public static void redo() {
        if (!canRedo()) {
            return;
        }
        Command command = redoStack.pop();
        command.redo();
        undoStack.push(command);
    }
}