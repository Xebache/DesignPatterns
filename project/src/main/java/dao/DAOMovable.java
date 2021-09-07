package dao;

import model.Container;
import java.util.List;


public interface DAOMovable<E, P extends Container<E>> {

    List<E> getAll(P container);

    E get(int id);

    void update(E e);

    void delete(E movable);

    void deleteAll(P container);

    void insert(E movable);

}
