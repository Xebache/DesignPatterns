package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public interface Movable <E, P extends Container<E>> {

    P getContainer();

    void setContainer(P container);

    E getMovable();

    //   GETTER

    default int getPosition() {
        return getContainer().getPosition(getMovable());
    }

    //   BOOLEAN PROPERTIES

    default BooleanProperty isFirstProperty() {
        return new SimpleBooleanProperty(getContainer().isFirst(getMovable()));
    }

    default BooleanProperty isLastProperty() {
        return new SimpleBooleanProperty(getContainer().isLast(getMovable()));
    }


    //   MOVE

    default void moveUp() {
        getContainer().moveUp(getMovable());
    }

    default void moveDown() {
        getContainer().moveDown(getMovable());
    }


    //   DELETE

    default void delete() {
        getContainer().remove(getMovable());
    }


    //   MEMENTO

    default PositionState<E, P> savePosition() {
        return new PositionState<>(this);
    }

    default void restore(PositionState<E, P> save) {
        P container = save.getContainer();
        int position = save.getPosition();
        setContainer(container);
        if(!container.getMovables().isEmpty())
            container.add(position, getMovable());
        else
            container.add(getMovable());
    }
}