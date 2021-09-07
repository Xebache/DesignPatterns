package model.repository;

import dao.DAOManager;
import model.Card;
import model.Column;


class RepositoryColumn implements RepositoryMovable<Column> {

    private final DAOManager daoManager = DAOManager.getInstance();

    @Override
    public void insert(Column column) {
        daoManager.getDaoColumn().insert(column);
        daoManager.close();
    }

    @Override
    public void update(Column column) {
        daoManager.getDaoColumn().update(column);
        daoManager.close();
    }

    @Override
    public void save(Column column) {
        for(Card card : column.getCards())
            daoManager.getDaoCard().update(card);
        update(column);
        daoManager.close();
    }

    @Override
    public void delete(Column column) {
        daoManager.getDaoCard().deleteAll(column);
        daoManager.getDaoColumn().delete(column);
        for(Column column1 : column.getContainer().getColumns())
            daoManager.getDaoColumn().update(column1);
        daoManager.close();
    }

}
