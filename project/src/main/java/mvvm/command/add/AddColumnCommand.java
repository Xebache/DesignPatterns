package mvvm.command.add;

import model.*;


public class AddColumnCommand extends AddCommand<Column, Board> {

    public AddColumnCommand(Board board) {
        super(board);
    }

    @Override
    public String toString() {
        return super.toString() + "d'une colonne";
    }

}
