package mvvm.command;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mvvm.command.serviceBoard.ServiceBoardCommand;
import java.util.Stack;


public class CommandManager {

    private static CommandManager commandManager;

    private final Stack<Command>
            undoCommands = new Stack<>(),
            redoCommands = new Stack<>();

    private final BooleanProperty
            undoHasNoCommand = new SimpleBooleanProperty(true),
            redoHasNoCommand = new SimpleBooleanProperty(true);

    private final StringProperty
            undoCommandText = new SimpleStringProperty(""),
            redoCommandText = new SimpleStringProperty("");


    //   CONSTRUCTORS

    private CommandManager() {
    }

    // Singleton pattern
    public static CommandManager getInstance() {
        if(commandManager == null) {
            commandManager = new CommandManager();
        }
        return commandManager;
    }


    //   METHODS

    public void execute(Command command) {
        command.execute();
        if(command instanceof ServiceBoardCommand) {
            resetUndo();
        }
        else {
            setUndo(command);
        }
        resetRedo();
    }

    public void undo() {
        Command command = undoCommands.pop();
        command.undo();
        Command previousCommand = undoCommands.empty() ? null : undoCommands.peek();
        setUndoProperties(previousCommand);
        setRedo(command);
    }

    public void redo() {
        Command command = redoCommands.pop();
        command.execute();
        setUndo(command);
        Command previousCommand = redoCommands.empty() ? null : redoCommands.peek();
        setRedoProperties(previousCommand);
    }

    private void resetRedo() {
        redoCommands.clear();
        setRedoProperties(null);
    }

    private void resetUndo() {
        undoCommands.clear();
        setUndoProperties(null);
    }

    private void setRedo(Command command) {
        redoCommands.push(command);
        setRedoProperties(command);
    }

    private void setUndo(Command command) {
        undoCommands.push(command);
        setUndoProperties(command);
    }


    //   PROPERTIES

    private void setUndoProperties(Command command) {
        undoHasNoCommand.set(undoCommands.isEmpty());
        if(command != null) {
            undoCommandText.set(command.toString());
        }
        else {
            undoCommandText.set("");
        }
    }

    private void setRedoProperties(Command command) {
        redoHasNoCommand.set(redoCommands.isEmpty());
        if(command != null) {
            redoCommandText.set(command.toString());
        }
        else {
            redoCommandText.set("");
        }
    }

    public BooleanProperty undoHasNoCommandProperty() {
        return undoHasNoCommand;
    }

    public BooleanProperty redoHasNoCommandProperty() {
        return redoHasNoCommand;
    }

    public StringProperty undoCommandTextProperty() {
        return undoCommandText;
    }

    public StringProperty redoCommandTextProperty() {
        return redoCommandText;
    }

}
