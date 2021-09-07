package model.repository;

import dao.DAOManager;
import model.Board;
import model.Card;
import model.Column;
import java.util.List;


class RepositoryBoard implements Repository<Board> {

    private final DAOManager daoManager = DAOManager.getInstance();

    public RepositoryBoard() {
    }

    @Override
    public Board get(int idBoard) {
        Board board = daoManager.getDaoBoard().get(idBoard);
        daoManager.close();
        return board;
    }

    @Override
    public void create()  {
        Board board = new Board();
        daoManager.getDaoBoard().insert(board);
        daoManager.close();
    }

    @Override
    public void save(Board board) {
        for (Column column : board.getColumns()) {
            for(Card card : column.getCards()) {
                daoManager.getDaoCard().update(card);
            }
            daoManager.getDaoColumn().update(column);
        }
        update(board);
        daoManager.close();
    }

    @Override
    public void update(Board board) {
        daoManager.getDaoBoard().update(board);
        daoManager.close();
    }

    @Override
    public List<Board> getAll()  {
        List<Board> boards = daoManager.getDaoBoard().getAll();
        daoManager.close();
        return boards;
    }


}
