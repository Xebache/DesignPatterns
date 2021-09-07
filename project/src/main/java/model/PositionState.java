package model;


public class PositionState<E, P extends Container<E>> {

    private final P container;
    private final int position;


    //   CONSTRUCTOR

    PositionState(Movable<E, P> movable) {
        this.container = movable.getContainer();
        this.position = movable.getContainer().getPosition(movable.getMovable());
    }


    //   GETTERS

    P getContainer() {
        return container;
    }

    int getPosition() {
        return position;
    }

}