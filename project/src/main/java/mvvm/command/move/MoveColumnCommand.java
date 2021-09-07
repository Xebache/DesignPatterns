package mvvm.command.move;

import direction.Direction;
import model.Board;
import model.Column;


public class MoveColumnCommand extends MoveCommand<Column, Board> {


    //   CONSTRUCTOR

    public MoveColumnCommand(Column column, Direction direction) {
        super(column, direction);
    }


    //   TO STRING

    @Override
    public String toString() {
        String direction = "vers la ";
        if(getDirection() == Direction.DOWN)
            direction += "droite";
        else
            direction += "gauche";
        return "Déplacement de la " + getMovable() + " " + direction;
    }

}