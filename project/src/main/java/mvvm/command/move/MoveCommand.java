package mvvm.command.move;

import direction.Direction;
import model.Container;
import model.Movable;
import mvvm.command.Command;


abstract class MoveCommand<E, P extends Container<E>> implements Command {

    private final Movable<E,P> movable;
    private final Direction direction;


    //   CONSTRUCTOR

    MoveCommand(Movable<E, P> movable, Direction direction) {
        this.movable = movable;
        this.direction = direction;
    }


    //   GETTERS

    Movable<E, P> getMovable() {
        return movable;
    }

    Direction getDirection() {
        return direction;
    }

    @Override
    public void execute() {
        switch (direction) {
            case UP:
                movable.moveUp();
                break;
            case DOWN:
                movable.moveDown();
                break;
        }
    }

    @Override
    public void undo() {
        switch (direction) {
            case UP:
                movable.moveDown();
                break;
            case DOWN:
                movable.moveUp();
                break;
        }
    }

}
