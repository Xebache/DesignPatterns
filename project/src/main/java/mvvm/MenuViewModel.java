package mvvm;

import javafx.beans.property.*;
import model.Column;
import model.Trello;
import mvvm.command.add.AddCardCommand;
import mvvm.command.add.AddColumnCommand;
import mvvm.command.CommandManager;
import javafx.beans.binding.Bindings;
import model.Board;
import mvvm.command.serviceBoard.CreateBoardCommand;
import mvvm.command.serviceBoard.SaveAllBoardsAndQuitCommand;
import mvvm.command.serviceBoard.SaveBoardCommand;


public class MenuViewModel {

    private final StringProperty
            undoCommandText = new SimpleStringProperty(""),
            redoCommandText = new SimpleStringProperty("");

    private final BooleanProperty
            miNewColumnDisabled = new SimpleBooleanProperty(true);

    private final BooleanProperty
            mNewCardDisabled = new SimpleBooleanProperty(true);

    private static final SimpleObjectProperty<Board>
            selectedBoard = new SimpleObjectProperty<>();

    private static final CommandManager
            commandManager = CommandManager.getInstance();

    private final ListProperty<Board>
            boards = new SimpleListProperty<>();

    public final ListProperty<Column>
            columns = new SimpleListProperty<>();


    //   CONSTRUCTOR

    public MenuViewModel() {
        boards.set(Trello.getAllBoards());
        setData();
    }


    //  LISTENER

    public void setData() {
        selectedBoard.addListener((obs, oldVal, newVal) -> {
            miNewColumnDisabled.set(newVal == null);
            boards.set(Trello.getAllBoards());
        });

        columns.addListener((obs, oldVal, newVal) ->
                mNewCardDisabled.set(BoardViewModel.getBoard() == null || columns.isEmpty())
        );
    }


    //   PROPERTIES

    public BooleanProperty imRedoDisabledProperty() {
        return (commandManager.redoHasNoCommandProperty());
    }

    public BooleanProperty imUndoDisabledProperty() {
        return (commandManager.undoHasNoCommandProperty());
    }

    public BooleanProperty miNewColumnDisabledProperty() {
        return miNewColumnDisabled;
    }

    public BooleanProperty mNewCardDisabledProperty() {
        return mNewCardDisabled;
    }

    public StringProperty undoCommandTextProperty() {
        undoCommandText.bind(Bindings.concat("Annuler \t").concat(commandManager.undoCommandTextProperty()));
        return undoCommandText;
    }

    public StringProperty redoCommandTextProperty() {
        redoCommandText.bind(Bindings.concat("Refaire \t").concat(commandManager.redoCommandTextProperty()));
        return redoCommandText;
    }

    public SimpleListProperty<Board> boardsProperty() {
        return new SimpleListProperty<>(boards);
    }

    public SimpleObjectProperty<Board> selectedBoardProperty() {
        return selectedBoard;
    }

    public ListProperty<Column> columnsProperty() {
        return columns;
    }


    //   METHODS

    public static void saveAllBoards() {
        commandManager.execute(new SaveAllBoardsAndQuitCommand());
    }

    public static void saveBoard(Board board) {
        commandManager.execute(new SaveBoardCommand(board));
    }

    public void newBoard() {
        commandManager.execute(new CreateBoardCommand());
        boards.set(Trello.getAllBoards());
    }

    public void addColumn() {
        commandManager.execute(new AddColumnCommand(BoardViewModel.getBoard()));
    }

    public void addCard(Column column) {
        commandManager.execute(new AddCardCommand(column));
    }

    public void undo() {
        commandManager.undo();
    }

    public void redo() {
        commandManager.redo();
    }

}