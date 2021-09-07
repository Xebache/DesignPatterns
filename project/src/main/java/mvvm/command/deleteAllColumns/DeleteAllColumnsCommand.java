package mvvm.command.deleteAllColumns;

import model.*;
import mvvm.command.Command;

import java.util.ArrayList;
import java.util.List;


public class DeleteAllColumnsCommand implements Command {

    private final Board board;
    private final List<Column> columnsToDelete;
    private final List<PositionState<Column, Board>> savedColumns = new ArrayList<>();


    //   CONSTRUCTOR

    public DeleteAllColumnsCommand(Board board) {
        this.board = board;
        this.columnsToDelete = new ArrayList<>(board.getSelectedColumns());
        for (Column column : columnsToDelete) {
            savedColumns.add(column.savePosition());
        }
    }


    //   METHODS

    @Override
    public void execute() {
        for (Column column : columnsToDelete ) {
            column.delete();
        }
    }

    @Override
    public void undo() {
        for (int i = 0; i < columnsToDelete.size(); ++i) {
            columnsToDelete.get(i).restore(savedColumns.get(i));
        }
    }


    //   TO STRING

    @Override
    public String toString() {
        return "Suppression massive ";
    }
}