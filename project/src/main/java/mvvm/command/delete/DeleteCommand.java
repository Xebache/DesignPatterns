package mvvm.command.delete;

import model.*;
import model.PositionState;
import mvvm.command.Command;


public abstract class DeleteCommand<E, P extends Container<E>> implements Command {

    private final Movable<E, P> movable;
    private final PositionState<E, P> save;


    //   CONSTRUCTOR

    public DeleteCommand(Movable<E, P> movable) {
        this.movable = movable;
        this.save = movable.savePosition();
    }


    //   METHODS

    @Override
    public void execute() {
        movable.delete();
    }

    @Override
    public void undo() {
        movable.restore(save);
    }


    //   TO STRING

    @Override
    public String toString() {
        return "Suppression de la " + movable;
    }

}