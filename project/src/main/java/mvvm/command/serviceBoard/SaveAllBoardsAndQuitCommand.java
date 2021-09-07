package mvvm.command.serviceBoard;

import model.Board;
import model.Trello;
import model.repository.RepositoryFactory;


import java.util.List;

public class SaveAllBoardsAndQuitCommand implements ServiceBoardCommand{

    public SaveAllBoardsAndQuitCommand() {
    }

    @Override
    public void execute() {
        List<Board> boards = Trello.getAllBoards();
        for(Board board : boards) {
            RepositoryFactory.getRepositoryBoard().save(board);
        }
        System.exit(0);
    }
}
