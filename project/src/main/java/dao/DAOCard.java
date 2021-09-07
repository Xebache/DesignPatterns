package dao;

import model.Card;
import model.Column;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


class DAOCard implements DAOMovable<Card, Column> {

    private final DAOManager daoManager;


    //   CONSTRUCTOR

    public DAOCard(DAOManager daoManager) {
        this.daoManager = daoManager;
    }


    //   MAPPER

    private Card map(ResultSet resultSet, Column column) throws SQLException {
        Card card = new Card(column);
        card.setId(resultSet.getInt("id"));
        card.setTitle(resultSet.getString("title"));
        return card;
    }


    //   QUERIES

    private static final String SQL_GET = "SELECT * FROM card WHERE id = ?;";
    private static final String SQL_GET_ALL = "SELECT * FROM card WHERE idColumn = ? ORDER BY position;";
    private static final String SQL_UPDATE = "UPDATE card SET title = ?, idColumn = ?, position = ? WHERE id = ?;";
    private static final String SQL_DELETE = "DELETE FROM card WHERE id = ?;";
    private static final String SQL_DELETE_ALL = "DELETE FROM card WHERE idColumn = ?;";
    private static final String SQL_INSERT = "INSERT INTO card (title, idColumn, position) VALUES (?, ?, ?);";


    //   METHODS

    @Override
    public Card get(int id) {
        Card card = null;

        try {
            Connection connection = daoManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idColumn = resultSet.getInt("idColumn");
                Column column = daoManager.getDaoColumn().get(idColumn);
                card = map(resultSet, column);
            }

        }

        catch (SQLException e) {
            throw new DAOException(e);
        }

        return card;
    }

    @Override
    public List<Card> getAll(Column column) {
        List<Card> cards = new ArrayList<>();

        try {
            Connection connection = daoManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL);
            preparedStatement.setInt(1, column.getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Card card = map(resultSet, column);
                int position = resultSet.getInt("position");
                cards.add(position, card);
            }

        }

        catch (SQLException e) {
            throw new DAOException(e);
        }

        return cards;
    }

    @Override
    public void update(Card card) {
        try {
            Connection connection = daoManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, card.getTitle());
            preparedStatement.setInt(2, card.getColumnId());
            preparedStatement.setInt(3, card.getPosition());
            preparedStatement.setInt(4, card.getId());

            preparedStatement.execute();
        }

        catch (SQLException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public void delete(Card card) {
        try {
            Connection connection = daoManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, card.getId());

            preparedStatement.execute();
        }

        catch (SQLException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public void deleteAll(Column column) {
        try {
            Connection connection = daoManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ALL);
            preparedStatement.setInt(1, column.getId());

            preparedStatement.execute();
        }

        catch (SQLException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public void insert(Card card) {
        try {
            Connection connection = daoManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, card.getTitle());
            preparedStatement.setInt(2, card.getColumnId());
            preparedStatement.setInt(3, card.getPosition());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()) {
                card.setId(resultSet.getInt(1));
            }

        }

        catch (SQLException e) {
            throw new DAOException(e);
        }

    }

}
