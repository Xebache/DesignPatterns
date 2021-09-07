package mvvm.command.serviceBoard;

import mvvm.command.Command;


public interface ServiceBoardCommand extends Command {

    @Override
    default void undo() {
        throw new UnsupportedOperationException();
    }

}
