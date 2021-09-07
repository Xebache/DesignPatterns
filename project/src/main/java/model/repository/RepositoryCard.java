package model.repository;

import dao.DAOManager;
import model.Card;


class RepositoryCard implements RepositoryMovable<Card> {

    private final DAOManager daoManager = DAOManager.getInstance();

    @Override
    public void insert(Card card) {
        daoManager.getDaoCard().insert(card);
        daoManager.close();
    }

    @Override
    public void update(Card card) {
        System.out.println(card.getTitle());
        daoManager.getDaoCard().update(card);
        daoManager.close();
    }

    @Override
    public void save(Card card) {
        update(card);
    }

    @Override
    public void delete(Card card) {
        daoManager.getDaoCard().delete(card);
        daoManager.close();
    }

}
