package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Collections;
import java.util.List;


public abstract class Container<E> extends Item {

    private ObservableList<E> movables = FXCollections.observableArrayList();


    //   CONSTRUCTOR

    public Container(String title) {
        super(title);
    }


    //   GETTERS

    ObservableList<E> getMovables() {
        return movables;
    }

    public int getPosition(E e) {
        return getMovables().indexOf(e);
    }

    void setMovables(List<E> movables) {
        this.movables = FXCollections.observableArrayList(movables);
    }

    //   BOOLEANS

    boolean isLast(E e) {
        return getPosition(e) == size() - 1;
    }

    boolean isFirst(E e) {
        return getPosition(e) == 0;
    }


    //   TOOLS

    int size() {
        return getMovables().size();
    }

    E getNext(E e) {
        if(!isLast(e)) {
            return getMovables().get(getPosition(e) + 1);
        }
        return null;
    }

    E getPrevious(E e) {
        if(!isFirst(e)) {
            return getMovables().get(getPosition(e) - 1);
        }
        return null;
    }

    private void swap(E e1, E e2) {
        Collections.swap(getMovables(), getPosition(e1), getPosition(e2));
    }


    //   MOVE

    void moveUp(E e) {
        if(!isFirst(e)) {
            swap(e, getPrevious(e));
        }
    }

    void moveDown(E e) {
        if(!isLast(e)) {
            swap(getNext(e), e);
        }
    }


    //   ADD & DELETE

    @SuppressWarnings("unchecked")
    public void deleteLast() {
        ObservableList<E> movables = this.getMovables();
        Movable<E, Container<E>> last = (Movable<E, Container<E>>) movables.get(size() - 1);
        last.delete();
    }

    void add(E e) {
        getMovables().add(e);
    }

    void add(int position, E e) {
        getMovables().add(position, e);
    }

    void remove(E e) {
        getMovables().remove(e);
    }

    public abstract void addNew();

}

