package mvvm.command.serviceBoard;

import model.Board;
import model.repository.RepositoryFactory;


public class SaveBoardCommand implements ServiceBoardCommand {

    private final Board board;

    public SaveBoardCommand(Board board) {
        this.board = board;
    }

    @Override
    public void execute() {
        RepositoryFactory.getRepositoryBoard().save(board);
    }

}
