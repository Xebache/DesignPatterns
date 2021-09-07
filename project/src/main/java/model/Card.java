package model;

import model.repository.RepositoryFactory;


public class Card extends Item implements Movable<Card, Column> {

    private Column column;


    //   CONSTRUCTORS

    public Card(Column column, String title) {
        super(title);
        this.column = column;
        column.add(this);
    }

    public Card(int position, Column column, String title) {
        super(title);
        this.column = column;
        column.add(position, this);
    }

    public Card(Column column) {
        this(column, "");
    }


    //   GETTERS

    @Override
    public Card getMovable() {
        return this;
    }

    @Override
    public Column getContainer() {
        return column;
    }

    public int getColumnId() {
        return getContainer().getId();
    }


    //   SETTER

    @Override
    public void setContainer(Column column) {
        this.column = column;
    }


    //   TOSTRING

    @Override
    public String toString() {
        return "carte " + getTitle();
    }


    //   MOVE

    @Override
    public void moveUp() {
        Movable.super.moveUp();
        RepositoryFactory.getRepositoryColumn().update(getContainer());
    }

    public void moveLeft() {
        Column previous = getPreviousColumn();
        if (previous != null) {
            switchTo(previous);
        }
    }

    public void moveRight() {
        Column next = getNextColumn();
        if (next != null) {
            switchTo(next);
        }
    }

    private void switchTo(Column newColumn) {
        column.remove(this);
        newColumn.add(this);
        setContainer(newColumn);
        RepositoryFactory.getRepositoryCard().update(this);
    }

    public Column getNextColumn() {
        Board board = column.getContainer();
        return board.getNext(column);
    }

    public Column getPreviousColumn() {
        Board board = column.getContainer();
        return board.getPrevious(column);
    }

    @Override
    public void restore(PositionState<Card, Column> save) {
        Movable.super.restore(save);
        RepositoryFactory.getRepositoryCard().insert(this);
    }


    //   DELETE

    @Override
    public void delete() {
        Movable.super.delete();
        RepositoryFactory.getRepositoryCard().delete(this);
    }

    //   EDIT

    @Override
    public void updateTitle(String title) {
        super.updateTitle(title);
        RepositoryFactory.getRepositoryCard().update(this);
    }

    @Override
    public TitleState saveTitle(String oldTitle) {
        RepositoryFactory.getRepositoryCard().update(this);
        return super.saveTitle(oldTitle);
    }

}