package model.repository;

import model.Board;
import model.Card;
import model.Column;


public abstract class RepositoryFactory {

    public static Repository<Board> getRepositoryBoard() {
        return new RepositoryBoard();
    }

    public static RepositoryMovable<Column> getRepositoryColumn() {
        return new RepositoryColumn();
    }

    public static RepositoryMovable<Card> getRepositoryCard() {
        return new RepositoryCard();
    }


}
