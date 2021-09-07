package mvvm;

import mvvm.command.*;
import javafx.beans.property.*;
import model.Board;
import model.Column;
import mvvm.command.add.AddColumnCommand;
import mvvm.command.delete.DeleteColumnCommand;


public class BoardViewModel {

    private final ListProperty<Column>
            columnsList = new SimpleListProperty<>();

    private final IntegerProperty
            selectedColumn = new SimpleIntegerProperty();

    private static Board board = null;

    private final LabelEditableViewModel labelEditableViewModel;

    private final CommandManager
            commandManager = CommandManager.getInstance();


    //   CONSTRUCTOR

    public BoardViewModel(Board board) {
        BoardViewModel.board = board;
        labelEditableViewModel = new LabelEditableViewModel(board);
        setColumnsList();
    }

    public static Board getBoard() {
        return board;
    }

    //   SETTER

    private void setColumnsList() {
        columnsList.set(board.getColumns());
    }


    //  PROPERTIES

    public StringProperty boardTitleProperty() {
        return labelEditableViewModel.itemTitleProperty();
    }

    public ListProperty<Column> columnsListProperty() {
        return columnsList;
    }

    private Column getColumn() {
        int index = selectedColumn.get();
        return index == -1 ? null : columnsList.get(index);
    }


    //   BINDINGS

    public void selectedColumnBinding(ReadOnlyIntegerProperty integerProperty) {
        selectedColumn.bind(integerProperty);
    }

    public void focusedTitleBinding(ReadOnlyBooleanProperty readOnlyBooleanProperty) {
        labelEditableViewModel.focusedTitleBinding(readOnlyBooleanProperty);
    }


    //   ACTIONS

    public void addColumn() {
        commandManager.execute(new AddColumnCommand(board));
    }

    public void delete() {
        Column column = getColumn();
        if(column != null) {
            commandManager.execute(new DeleteColumnCommand(column));
        }
    }

}