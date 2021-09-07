package mvvm.command.add;

import model.*;
import mvvm.command.Command;


public abstract class AddCommand<E, P extends Container<E>> implements Command {

    private final P container;


    //   CONSTRUCTOR

    public AddCommand(P container) {
        this.container = container;
    }


    //   METHODS

    @Override
    public void execute() {
        container.addNew();
    }

    @Override
    public void undo() {
        container.deleteLast();
    }

    @Override
    public String toString() {
        return "Ajout ";
    }

}