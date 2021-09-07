package dao;

import model.Board;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


class DAOBoard implements DAO<Board> {

    private final DAOManager daoManager;


    //   CONSTRUCTOR

    public DAOBoard(DAOManager daoManager) {
        this.daoManager = daoManager;
    }


    //   MAPPER

    private Board map(ResultSet resultSet) throws SQLException {
        Board board = new Board();
        board.setId(resultSet.getInt("id"));
        board.setTitle(resultSet.getString("title"));
        board.setColumns(daoManager.getDaoColumn().getAll(board));
        return board;
    }


    //   QUERIES

    private static final String SQL_GET_ALL = "SELECT * FROM board;";
    private static final String SQL_BOARD = "SELECT * FROM board WHERE id = ?;";
    private static final String SQL_UPDATE = "UPDATE board SET title = ? WHERE id = ?;";
    private static final String SQL_INSERT = "INSERT INTO board (title) VALUES (?);";


    //   METHODS

    public List<Board> getAll() {
        List<Board> boards = new ArrayList<>();

        try {
            Connection connection = daoManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Board board = map(resultSet);
                boards.add(board);
            }

        }

        catch (SQLException e) {
            throw new DAOException(e);
        }

        return boards;
    }

    @Override
    public Board get(int id) {
        Board board = null;

        try {
            Connection connection = daoManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_BOARD);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                board = map(resultSet);
            }

        }

        catch (SQLException e) {
            throw new DAOException(e);
        }

        return board;
    }

    @Override
    public void insert(Board board) {
        try {
            Connection connection = daoManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, board.getTitle());

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()) {
                board.setId(resultSet.getInt(1));
            }

        }

        catch (SQLException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public void update(Board board) {
        try {
            Connection connection = daoManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, board.getTitle());
            preparedStatement.setInt(2, board.getId());

            preparedStatement.execute();
            preparedStatement.getGeneratedKeys();
        }

        catch (SQLException e) {
            throw new DAOException(e);
        }

    }

}
