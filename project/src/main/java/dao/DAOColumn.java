package dao;

import model.Board;
import model.Card;
import model.Column;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


class DAOColumn implements DAOMovable<Column, Board> {

    private final DAOManager daoManager;


    //   CONSTRUCTOR

    public DAOColumn(DAOManager daoManager) {
        this.daoManager = daoManager;
    }


    //   MAPPER

    private Column map(ResultSet resultSet, Board board) throws SQLException {
        Column column = new Column(board);
        column.setId(resultSet.getInt("id"));
        column.setTitle(resultSet.getString("title"));
        column.setCards(daoManager.getDaoCard().getAll(column));
        return column;
    }


    //   QUERIES

    private static final String SQL_GET = "SELECT * FROM column WHERE id = ?;";
    private static final String SQL_GET_ALL = "SELECT * FROM column WHERE idBoard = ? ORDER BY position;";
    private static final String SQL_UPDATE = "UPDATE column SET title = ?, position = ? WHERE id = ?;";
    private static final String SQL_DELETE = "DELETE FROM column WHERE id = ?;";
    private static final String SQL_DELETE_ALL = "DELETE FROM column WHERE idBoard = ?;";
    private static final String SQL_INSERT = "INSERT INTO column (title, idBoard, position) VALUES (?, ?, ?);";


    //   METHODS

    @Override
    public Column get(int id) {
        Column column = null;

        try {
            Connection connection = daoManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idBoard = resultSet.getInt("idBoard");
                Board board = daoManager.getDaoBoard().get(idBoard);
                column = map(resultSet, board);
            }

        }

        catch (SQLException e) {
            throw new DAOException(e);
        }

        return column;
    }

    @Override
    public List<Column> getAll(Board board) {
        List<Column> columns = new ArrayList<>();

        try {
            Connection connection = daoManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL);
            preparedStatement.setInt(1, board.getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Column column = map(resultSet, board);
                int position = resultSet.getInt("position");
                columns.add(position, column);
            }

        }

        catch (SQLException e) {
            throw new DAOException(e);
        }

        return columns;

    }

    @Override
    public void update(Column column) {
        try {
            Connection connection = daoManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, column.getTitle());
            preparedStatement.setInt(2, column.getPosition());
            preparedStatement.setInt(3, column.getId());

            preparedStatement.execute();
        }

        catch (SQLException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public void delete(Column column) {
        try {
            Connection connection = daoManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, column.getId());

            preparedStatement.execute();
        }

        catch (SQLException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public void deleteAll(Board board) {
        try {
            Connection connection = daoManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ALL);
            preparedStatement.setInt(1, board.getId());

            preparedStatement.execute();
        }

        catch (SQLException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public void insert(Column column) {
        try {
            Connection connection = daoManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, column.getTitle());
            preparedStatement.setInt(2, column.getBoardId());
            preparedStatement.setInt(3, column.getPosition());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()) {
                column.setId(resultSet.getInt(1));
            }
            for (Card card : column.getCards()) {
                daoManager.getDaoCard().insert(card);
            }

        }

        catch (SQLException e) {
            throw new DAOException(e);
        }

    }

}
