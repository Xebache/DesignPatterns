package mvvm.command.delete;

import model.Board;
import model.Column;


public class DeleteColumnCommand extends DeleteCommand<Column, Board> {

    public DeleteColumnCommand(Column column) {
        super(column);
    }

}
