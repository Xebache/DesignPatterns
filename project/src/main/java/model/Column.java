package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.repository.RepositoryFactory;
import java.util.List;


public class Column extends Container<Card> implements Movable<Column, Board> {

    private Board board;
    private boolean isSelected;

    //   CONSTRUCTORS

    public Column(Board board, String title) {
        super(title);
        this.board = board;
        board.add(this);
    }

    public Column(int position, Board board, String title, ObservableList<Card> cards) {
        super(title);
        this.board = board;
        board.add(position, this);
        getMovables().addAll(cards);
    }

    public Column(Board board) {
        this(board, "");
    }


    //   GETTERS

    @Override
    public Board getContainer() {
        return board;
    }

    @Override
    public Column getMovable() {
        return this;
    }

    public ObservableList<Card> getCards() {
        return FXCollections.unmodifiableObservableList(this.getMovables());
    }

    public int getBoardId() {
        return getContainer().getId();
    }

    public boolean getIsSelected() {
        return isSelected;
    }
    //   SETTER

    @Override
    public void setContainer(Board board) {
        this.board = board;
    }

    public void setCards(List<Card> cards) {
        setMovables(cards);
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    //   TOSTRING

    @Override
    public String toString() {
        return "colonne \"" + getTitle() + "\"";
    }


    //   ADD

    @Override
    public void addNew() {
        Card card = new Card(this);
        RepositoryFactory.getRepositoryCard().insert(card);
    }


    //   DELETE

    @Override
    public void delete() {
        Movable.super.delete();
        RepositoryFactory.getRepositoryColumn().delete(this);
    }


    //   EDIT

    @Override
    public void updateTitle(String title) {
        super.updateTitle(title);
        RepositoryFactory.getRepositoryColumn().update(this);
    }

    @Override
    public TitleState saveTitle(String oldTitle) {
        RepositoryFactory.getRepositoryColumn().update(this);
        return super.saveTitle(oldTitle);
    }


    //   MOVE

    @Override
    public void moveUp() {
        Movable.super.moveUp();
        RepositoryFactory.getRepositoryBoard().save(getContainer());
    }

    @Override
    public void moveDown() {
        Movable.super.moveDown();
        RepositoryFactory.getRepositoryBoard().save(getContainer());
    }

    @Override
    public void restore(PositionState<Column, Board> save) {
        Movable.super.restore(save);
        RepositoryFactory.getRepositoryColumn().insert(this);
    }

    public BooleanProperty isSelectedProperty() {
        return new SimpleBooleanProperty(isSelected);
    }

}