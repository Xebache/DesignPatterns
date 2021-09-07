package mvvm.command.serviceBoard;

import model.repository.RepositoryFactory;


public class CreateBoardCommand implements ServiceBoardCommand {

    @Override
    public void execute() {
        RepositoryFactory.getRepositoryBoard().create();
    }

}
